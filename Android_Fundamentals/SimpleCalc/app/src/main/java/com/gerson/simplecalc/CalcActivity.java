package com.gerson.simplecalc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.text.TextUtils;


import android.view.*;
import android.widget.*;

public class CalcActivity extends Activity {

    //TODO: saving state

    private static final String LOGTAG = "CALC_ACTIVITY";

    private EditText mOperandOneEditText;
    private EditText mOperandTwoEditText;
    private Button addButton;
    private Button subButton;
    private Button mulButton;
    private Button divButton;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region relativeLayout ...
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(
            new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        );
        //endregion

        //region mOperandOneEditText ...
        mOperandOneEditText = new EditText(this);
        mOperandOneEditText.setId(View.generateViewId());
        Log.d(LOGTAG,"mOperandOneEditText.getId():" + mOperandOneEditText.getId());
        mOperandOneEditText.setHint("Fist Operator (number)");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        relativeLayout.addView(
            mOperandOneEditText,
            layoutParams
        );
        //endregion

        //region mOperandTwoEditText ...
        mOperandTwoEditText = new EditText(this);
        mOperandTwoEditText.setId(View.generateViewId());
        Log.d(LOGTAG,"mOperandOneEditText.getId():" + mOperandTwoEditText.getId());
        mOperandTwoEditText.setHint("Second Operator (number)");
        layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.BELOW, mOperandOneEditText.getId());
        relativeLayout.addView(
                mOperandTwoEditText,
                layoutParams
        );
        //endregion

        //region calcToolBar ...
        LinearLayout calcToolBar = new LinearLayout(this);
        calcToolBar.setId(View.generateViewId());
        Log.d(LOGTAG,"calcToolBar.getId():" + calcToolBar.getId());
        layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.BELOW, mOperandTwoEditText.getId());
        relativeLayout.addView(
                calcToolBar,
                layoutParams
        );
        //endregion

        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );

        //region addButton ...

        addButton = new Button(this);
        addButton.setId(View.generateViewId());
        Log.d(LOGTAG,"addButton.getId():" + addButton.getId());
        addButton.setText("+");
        addButton.setOnClickListener(
            new View.OnClickListener() {
                @Override public void onClick(View v) { compute(Operator.ADD); }
            }
        );
        calcToolBar.addView( addButton, buttonsParams );

        //endregion

        //region subButton ...

        subButton = new Button(this);
        subButton.setId(View.generateViewId());
        Log.d(LOGTAG,"addButton.getId():" + subButton.getId());
        subButton.setText("-");
        subButton.setOnClickListener(
            new View.OnClickListener() {
                @Override public void onClick(View v) { compute(Operator.SUB); }
            }
        );
        calcToolBar.addView( subButton, buttonsParams );

        //endregion

        //region mulButton ...

        mulButton = new Button(this);
        mulButton.setId(View.generateViewId());
        Log.d(LOGTAG,"addButton.getId():" + mulButton.getId());
        mulButton.setText("*");
        mulButton.setOnClickListener(
            new View.OnClickListener() {
                @Override public void onClick(View v) { compute(Operator.MUL); }
            }
        );
        calcToolBar.addView( mulButton, buttonsParams );

        //endregion

        //region divButton ...

        divButton = new Button(this);
        divButton.setId(View.generateViewId());
        Log.d(LOGTAG,"addButton.getId():" + divButton.getId());
        divButton.setText("/");
        divButton.setOnClickListener(
            new View.OnClickListener() {
                @Override public void onClick(View v) {
                    try { compute(Operator.DIV); }
                    catch (IllegalArgumentException iae) {
                        Log.e(LOGTAG, "IllegalArgumentException", iae);
                        mResultTextView.setText("Number parsing error");
                    }
                }
            }
        );
        calcToolBar.addView( divButton, buttonsParams );
        //endregion

        //region mResultTextView ...
        mResultTextView = new TextView(this);
        mResultTextView.setId(View.generateViewId());
        mResultTextView.setTextSize(30);
        mResultTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mResultTextView.setText("RESULT");
        mResultTextView.setBackgroundColor(0x0000FF00);
        Log.d(LOGTAG,"mResultTextView.getId():" + mResultTextView.getId());
        layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.BELOW, calcToolBar.getId());
        relativeLayout.addView(
                mResultTextView,
                layoutParams
        );
        //endregion

        this.setContentView(relativeLayout);
    }

    //region compute ...

    // Available operations
    public enum Operator {ADD, SUB, DIV, MUL}

    private Calculator calculator = new Calculator();

    private void compute(CalcActivity.Operator operator) {
        double operandOne;
        double operandTwo;
        try {
            operandOne = getOperand(mOperandOneEditText);
            operandTwo = getOperand(mOperandTwoEditText);
        } catch (NumberFormatException nfe) {
            Log.e(LOGTAG, "NumberFormatException", nfe);
            mResultTextView.setText("Number parsing error");
            return;
        }

        String result;
        switch (operator) {
            case ADD:
                result = String.valueOf(
                        calculator.add(operandOne, operandTwo));
                break;
            case SUB:
                result = String.valueOf(
                        calculator.sub(operandOne, operandTwo));
                break;
            case DIV:
                result = String.valueOf(
                        calculator.div(operandOne, operandTwo));
                break;
            case MUL:
                result = String.valueOf(
                        calculator.mul(operandOne, operandTwo));
                break;
            default:
                result = "Number parsing error";
                break;
        }
        mResultTextView.setText(result);
    }

    /**
     * @return the operand value entered in an EditText as double.
     */
    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }

    /**
     * @return the operand text which was entered in an EditText.
     */
    private static String getOperandText(EditText operandEditText) {
        String operandText = operandEditText.getText().toString();
        if (TextUtils.isEmpty(operandText)) {
            throw new NumberFormatException("Operand cannot be empty!");
        }
        return operandText;
    }

    /**
     * Utility class for SimpleCalc to perform the actual calculations.
     */
    public class Calculator {

        /**
        * Addition operation
        */
        public double add(double firstOperand, double secondOperand) {
            return firstOperand + secondOperand;
        }

        /**
         * Subtract operation
         */
        public double sub(double firstOperand, double secondOperand) {
            return firstOperand - secondOperand;
        }

        /**
         * Divide operation
         */
        public double div(double firstOperand, double secondOperand) throws IllegalArgumentException {
            if (secondOperand == 0 ) {
              throw new IllegalArgumentException("You cannot divide by zero");
            }
            return firstOperand / secondOperand;
        }

        /**
         * Multiply operation
         */
        public double mul(double firstOperand, double secondOperand) {
            return firstOperand * secondOperand;
        }
    }

    //endregion
}
