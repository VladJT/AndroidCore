package jt.projects.androidcore.calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.util.Map;

import jt.projects.androidcore.R;
import jt.projects.androidcore.examples.StylesActivity;

public class CalculatorActivity extends BaseActivity {
    private final static String CALC_DATA_KEY = "calculator_data"; //  CalcData - Parcelable

    private TextView tResult;
    private TextInputEditText eInputNumber;
    private TextInputLayout tInputNumberLayout;
    private CalcData calcData;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b0;
    private Button bDot;
    private Button bAdd;
    private Button bSubstract;
    private Button bMultiply;
    private Button bDivide;
    private Button bClear;
    private Button bNegativePositive;
    private Button bDelete;
    private Button bResult;
    private Button bDarkheme;
    private Button bLightheme;
    private Button bThemeDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calcData = new CalcData();
        initViewComponents();
        //initThemeChooser();

        //     testNewActivity();
    }

    private void testNewActivity() {
        Intent intent = new Intent(CalculatorActivity.this, StylesActivity.class);
        startActivity(intent);
    }

    private void initViewComponents() {
        bClear = findViewById(R.id.button_clear);
        b1 = findViewById(R.id.button_1);
        b2 = findViewById(R.id.button_2);
        b3 = findViewById(R.id.button_3);
        b4 = findViewById(R.id.button_4);
        b5 = findViewById(R.id.button_5);
        b6 = findViewById(R.id.button_6);
        b7 = findViewById(R.id.button_7);
        b8 = findViewById(R.id.button_8);
        b9 = findViewById(R.id.button_9);
        b0 = findViewById(R.id.button_0);
        bDot = findViewById(R.id.button_dot);
        bAdd = findViewById(R.id.button_add);
        bSubstract = findViewById(R.id.button_substract);
        bMultiply = findViewById(R.id.button_multiply);
        bDivide = findViewById(R.id.button_divide);
        bDelete = findViewById(R.id.button_delete);
        bResult = findViewById(R.id.button_result);
        bNegativePositive = findViewById(R.id.button_negative_positive);
        tResult = findViewById(R.id.textViewResult);
        eInputNumber = findViewById(R.id.editTextInputNumber);
        tInputNumberLayout = findViewById(R.id.textInputLayout);
        bLightheme = findViewById(R.id.button_light_theme);
        bDarkheme = findViewById(R.id.button_dark_theme);
        bThemeDialog = findViewById(R.id.button_theme_dialog);

        // set listeners
        initNumberButtonsListeners();

        initOperatorButtonsListeners();

        initButtonResultListener();

        // кнопка [ C ]
        bClear.setOnClickListener(v ->
        {
            tResult.setText("");
            eInputNumber.setError(null);
            eInputNumber.setText("0");
            calcData.clear();
        });

        // кнопка [ <| ]
        bDelete.setOnClickListener(v ->
        {
            String s = eInputNumber.getText().toString();
            if (s.length() > 0) s = s.substring(0, s.length() - 1);
            if (s.length() == 0 || s.equals("-")) s = "0";
            eInputNumber.setText(s);
        });

        // кнопка [ +/- ]
        bNegativePositive.setOnClickListener(v ->
        {
            try {
                String inputString = eInputNumber.getText().toString();
                if (inputString.length() > 0 && !inputString.equals("0")) {
                    eInputNumber.setText(inputString.startsWith("-") ? inputString.substring(1) : ("-" + inputString));
                    calcData.setOperatorPressed(false);
                }
            } catch (Exception e) {
                showLogMessage(v.getContext(), e.getMessage());
            }
        });

        bDarkheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppTheme(themesMap.get(0));
                recreate();
            }
        });

        bLightheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppTheme(themesMap.get(1));
                recreate();
            }
        });

        bThemeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThemeDialog();
            }
        });
    }


    private void initButtonResultListener() {
        // кнопка [ = ]
        bResult.setOnClickListener(v -> {
            try {
                String infoString = "";
                calcData.setNumber(new BigDecimal(eInputNumber.getText().toString()));
                if (calcData.getNumber1() != null) {
                    infoString += calcData.getNumber1().toString();
                }
                if (!calcData.getOperator().equals("")) {
                    infoString += calcData.getOperator();
                    if (calcData.getNumber2() != null) {
                        String result = calcData.countResult();
                        if (result.equals(CalcData.DIVIDE_BY_ZERO_ERROR)) {
                            eInputNumber.setError("Деление на ноль запрещено");
                        //    tInputNumberLayout.setError("Деление на ноль запрещено");
                        } else {
                            eInputNumber.setError(null);
                            infoString += calcData.getNumber2() + "=";
                            calcData.setNumber1(calcData.getResult());
                            eInputNumber.setText(calcData.getResult().toString());
                        }
                    }
                }
                tResult.setText(infoString);
            } catch (Exception e) {
                showLogMessage(v.getContext(), e.getMessage());
            }
        });
    }

    private void initOperatorButtonsListeners() {
        // кнопки [ + ], [ - ], [ / ], [ * ]
        View.OnClickListener buttonOperatorClickListener = v -> {
            try {
                eInputNumber.setError(null);
                String operator = ((Button) v).getText().toString();
                calcData.setNumber(new BigDecimal(eInputNumber.getText().toString()));
                calcData.setOperator(operator);
                String infoText = calcData.getNumber1().toString() + operator;
                tResult.setText(infoText);
            } catch (Exception e) {
                showLogMessage(v.getContext(), e.getMessage());
            }
        };
        bAdd.setOnClickListener(buttonOperatorClickListener);
        bSubstract.setOnClickListener(buttonOperatorClickListener);
        bDivide.setOnClickListener(buttonOperatorClickListener);
        bMultiply.setOnClickListener(buttonOperatorClickListener);
    }

    private void initNumberButtonsListeners() {
        // кнопки [ 0 ] ... [ 9 ], [ . ]
        View.OnClickListener buttonNumberClickListener = v -> {
            try {
                eInputNumber.setError(null);
                String oldValue = eInputNumber.getText().toString();
                if (calcData.isOperatorPressed()) oldValue = "";
                String stNewValue = oldValue + ((Button) v).getText().toString();
                if (stNewValue.equals(".")) stNewValue = "0.";
                Double.parseDouble(stNewValue);// проверка на число
                eInputNumber.setText(StringFormatter.getNumberWithoutZerosAtStart(stNewValue));// удаляем нули перед числами
                calcData.setOperatorPressed(false);
            } catch (NumberFormatException e) {
                showLogMessage(v.getContext(), "Некорректное число");
            }
        };
        b1.setOnClickListener(buttonNumberClickListener);
        b2.setOnClickListener(buttonNumberClickListener);
        b3.setOnClickListener(buttonNumberClickListener);
        b4.setOnClickListener(buttonNumberClickListener);
        b5.setOnClickListener(buttonNumberClickListener);
        b6.setOnClickListener(buttonNumberClickListener);
        b7.setOnClickListener(buttonNumberClickListener);
        b8.setOnClickListener(buttonNumberClickListener);
        b9.setOnClickListener(buttonNumberClickListener);
        b0.setOnClickListener(buttonNumberClickListener);
        bDot.setOnClickListener(buttonNumberClickListener);
    }

//    private void initThemeChooser() {
//        initRadioButton(findViewById(R.id.radioButtonDark), themesMap.get(0));
//        initRadioButton(findViewById(R.id.radioButtonLight), themesMap.get(1));
//        RadioGroup rg = findViewById(R.id.radioButtonsTheme);
//        int checkedRbIndex = 0;
//        for (Map.Entry k : themesMap.entrySet()) {
//            if (Integer.valueOf(k.getValue().toString()) == getAppTheme()) {
//                checkedRbIndex = Integer.valueOf(k.getKey().toString());
//            }
//        }
//        ((MaterialRadioButton) rg.getChildAt(checkedRbIndex)).setChecked(true);
//    }
//
//    private void initRadioButton(View button, final int codeStyle) {
//        button.setOnClickListener(v -> {
//            setAppTheme(codeStyle);
//            recreate();
//        });
//    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        calcData.setResultInfoText(tResult.getText().toString());
        outState.putParcelable(CALC_DATA_KEY, calcData);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calcData = savedInstanceState.getParcelable(CALC_DATA_KEY);
        tResult.setText(calcData.getResultInfoText());
    }
}