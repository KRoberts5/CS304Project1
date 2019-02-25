package com.example.jsu.project1;

public class Operand {

    public static final String ZERO = "0";
    public static final int MAX_BUFFER_SIZE = 16;

    private boolean floating;
    private boolean positive;
    private boolean percentage;
    private String output;
    private Double value;

    public Operand(){
        floating = false;
        positive = true;
        percentage = false;
        output = "0";
        value = new Double(0);
    }

    public Operand(double initValue){
        value = new Double(initValue);
        output = value.toString();

        percentage = false;

        if(output.startsWith("-"))
            positive = false;
        else
            positive = true;

       checkIfFloating();
    }

    public void appendNumber(String num){

        if((value == 0) && !floating){

            output = num;

            if(!positive)
                output = "-" + output;

        }
        else{
            if(output.length() < MAX_BUFFER_SIZE)
                output += num;
        }

        updateValue();
    }

    public void changeSign(){
        positive = !positive;

        if(!positive)
            output = "-" + output;
        else{
            if(output.startsWith("-"))
                output = output.substring(1);
        }

        updateValue();
    }

    public void setFloating(){
        if(!floating && !this.inScientificNotation()){
            floating = true;
            output += ".";
        }
        updateValue();
    }


    public void findSquareRoot(){
        this.value = Math.sqrt(this.value);
        this.output = String.valueOf(this.value);
        checkIfFloating();
    }

    private boolean inScientificNotation(){

        boolean inSN = false;

        for(int i = 0; i < output.length(); ++i){
            char c = output.charAt(i);
            if(Character.isLetter(c))
                inSN = true;
        }

        return inSN;
    }

    private void updateValue(){

        if(output.isEmpty() || output.equals("-") || output.equals("."))
            value = new Double(0);
        else
            value = Double.valueOf(output);
    }

    private void checkIfFloating(){
        if((value%1) == 0){
            if(!output.contains("E")) {
                int decimalIndex = output.indexOf('.');
                output = output.substring(0, decimalIndex);

            }
            floating = false;
        }
        else
            floating = true;
    }

    public double getValue(){
        return value;
    }
    public String getOutput(){
        return output;
    }
}
