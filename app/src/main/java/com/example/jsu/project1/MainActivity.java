package com.example.jsu.project1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private double result;
    private Operand operand1;
    private Operand operand2;
    private String currentOperation;
    private String previousOperation;
    private String previousButtonClicked;
    private boolean equalChain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result = 0;
        currentOperation = "";
        operand1 = new Operand();
        operand2 = new Operand();

        previousOperation = "";
        previousButtonClicked = "";
        equalChain = false;

        setOutput(operand2.getOutput());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void retrieveInput(View v){

        String buttonClickedID = (v.getResources().getResourceName(v.getId())).split("/")[1];
        TextView outputView = (TextView)findViewById(R.id.outputView);

        if(currentOperation.equals(EQUAL)){
            if(buttonClickedIsNum(buttonClickedID)){
                resetCalculator();
            }
            else if(buttonClickedIsOperator(buttonClickedID)){
                resetCalculator(result);
            }
            else if(buttonClickedModifiesOperand(buttonClickedID)){
                resetCalculator(result);
            }
        }

        if(buttonClickedIsOperator(previousButtonClicked)){
            if(buttonClickedIsOperator(buttonClickedID))
                resetCalculator(operand1.getValue());
        }
        else if(previousButtonClicked.equals(SQUARE_ROOT) || previousButtonClicked.equals(PERCENT)){
            if(this.buttonClickedIsNum(buttonClickedID)){
                resetCalculator();
            }
        }

        switch(buttonClickedID){
            case NUM_0:
                operand2.appendNumber("0");
                setOutput(operand2.getOutput());
                break;
            case NUM_1:
                operand2.appendNumber("1");
                setOutput(operand2.getOutput());
                break;
            case NUM_2:
                operand2.appendNumber("2");
                setOutput(operand2.getOutput());
                break;
            case NUM_3:
                operand2.appendNumber("3");
                setOutput(operand2.getOutput());
                break;
            case NUM_4:
                operand2.appendNumber("4");
                setOutput(operand2.getOutput());
                break;
            case NUM_5:
                operand2.appendNumber("5");
                setOutput(operand2.getOutput());
                break;
            case NUM_6:
                operand2.appendNumber("6");
                setOutput(operand2.getOutput());
                break;
            case NUM_7:
                operand2.appendNumber("7");
                setOutput(operand2.getOutput());
                break;
            case NUM_8:
                operand2.appendNumber("8");
                setOutput(operand2.getOutput());
                break;
            case NUM_9:
                operand2.appendNumber("9");
                setOutput(operand2.getOutput());
                break;
            case SIGN_CHANGE:
                operand2.changeSign();
                setOutput(operand2.getOutput());
                break;
            case DECIMAL_POINT:
                operand2.setFloating();
                setOutput(operand2.getOutput());
                break;
            case ADD:
                submitCurrentOperation();
                currentOperation = ADD;
                setOutput(result);
                break;
            case SUBTRACT:
                submitCurrentOperation();
                currentOperation = SUBTRACT;
                setOutput(result);
                break;
            case DIVIDE:
                submitCurrentOperation();
                currentOperation = DIVIDE;
                setOutput(result);
                break;
            case MULTIPLY:
                submitCurrentOperation();
                currentOperation = MULTIPLY;
                setOutput(result);
                break;
            case CLEAR:
                resetCalculator();
                setOutput(result);
                break;
            case PERCENT:
                double percent = this.findPercent();
                operand2 = new Operand(percent);
                setOutput(operand2.getOutput());
                break;
            case SQUARE_ROOT:
                operand2.findSquareRoot();
                setOutput(operand2.getOutput());
                break;
            case EQUAL:
                equal();
                currentOperation = EQUAL;
                setOutput(result);
                break;
        }

        previousButtonClicked = buttonClickedID;
    }

    private void submitCurrentOperation(){

        double operand1 = this.operand1.getValue();
        double operand2 = this.operand2.getValue();

        switch (currentOperation) {
            case ADD:
                result = operand1 + operand2;
                break;
            case SUBTRACT:
                result = operand1 - operand2;
                break;
            case DIVIDE:
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else
                    result = operand2;
                break;
            case MULTIPLY:
                result = operand1 * operand2;
                break;
            default:
                result = operand2;
        }
        this.operand1 = new Operand(result);
        this.operand2 = new Operand();
    }

    private double findPercent(){

        double percent = 0;
        if((currentOperation.equals(ADD)) || (currentOperation.equals(SUBTRACT))){
            double op1 = operand1.getValue();
            double op2 = operand2.getValue();

            percent = (op1 * op2)/100;
        }

        else{
            double op2 = operand2.getValue();

            percent = op2/100;
        }


        return percent;
    }

    private void equal(){
        if(!equalChain){
            equalChain = true;
            this.previousOperation = currentOperation;
        }

        double op1 = result;
        double op2 = this.operand2.getValue();

        switch (previousOperation){
            case ADD:
                result = op1 + op2;
                break;
            case SUBTRACT:
                result = op1 - op2;
                break;
            case DIVIDE:
                if(op2 != 0){
                    result = op1 / op2;
                }
                else
                    result = op2;
                break;
            case MULTIPLY:
                result = op1 * op2;
                break;
            default:
                result = op2;
        }

    }

    private boolean buttonClickedIsNum(String buttonId){

        boolean isNum = false;
        switch(buttonId) {
            case NUM_0: case NUM_1: case NUM_2: case NUM_3: case NUM_4:
            case NUM_5: case NUM_6: case NUM_7: case NUM_8: case NUM_9:
                isNum = true;
                break;
        }

        return isNum;
    }

    private boolean buttonClickedModifiesOperand(String buttonId){
        boolean isMod = false;

        switch (buttonId){
            case DECIMAL_POINT: case SIGN_CHANGE: case PERCENT: case SQUARE_ROOT:
                isMod = true;
                break;
        }
        return isMod;
    }

    private boolean buttonClickedIsOperator(String buttonId){
        boolean isOp = false;

        switch(buttonId){
            case ADD: case SUBTRACT: case DIVIDE: case MULTIPLY:
                isOp = true;
                break;
        }

        return isOp;
    }

    private void resetCalculator(){
        result = 0;
        operand1 = new Operand();
        operand2 = new Operand();
        currentOperation = "";
        equalChain = false;
        previousOperation = "";
        previousButtonClicked = "";
    }

    private void resetCalculator(double op2){
        result = 0;
        operand1 = new Operand();
        operand2 = new Operand(op2);
        currentOperation = "";
        equalChain = false;
        previousOperation = "";
        previousButtonClicked = "";
    }

    private void resetCalculator(Operand op2){
        result = 0;
        operand1 = new Operand();
        operand2 = op2;
        currentOperation = "";
        equalChain = false;
        previousOperation = "";
    }

    private void setOutput(String output){
        TextView outputView = (TextView)findViewById(R.id.outputView);
        outputView.setText(output);
    }

    private void setOutput(double num){
        TextView outputView = (TextView)findViewById(R.id.outputView);

        String output = String.valueOf(num);

        if((num%1) == 0){
            if(!output.contains("E")) {
                int decimalIndex = output.indexOf('.');
                output = output.substring(0, decimalIndex);
            }
        }

        outputView.setText(output);
    }

    public static final String NUM_0 = "input0";
    public static final String NUM_1 = "input1";
    public static final String NUM_2 = "input2";
    public static final String NUM_3 = "input3";
    public static final String NUM_4 = "input4";
    public static final String NUM_5 = "input5";
    public static final String NUM_6 = "input6";
    public static final String NUM_7 = "input7";
    public static final String NUM_8 = "input8";
    public static final String NUM_9 = "input9";
    public static final String SIGN_CHANGE = "inputSignChange";
    public static final String DECIMAL_POINT = "inputDecimalPoint";
    public static final String ADD = "inputAdd";
    public static final String SUBTRACT = "inputSubtract";
    public static final String DIVIDE = "inputDivision";
    public static final String MULTIPLY = "inputMultiplication";
    public static final String CLEAR = "inputClear";
    public static final String EQUAL = "inputEqual";
    public static final String PERCENT = "inputPercent";
    public static final String SQUARE_ROOT = "inputSquareRoot";

}
