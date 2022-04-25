package jt.projects.androidcore.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import jt.projects.androidcore.R;

public class CalculatorActivity extends AppCompatActivity {
    private final static String CALC_DATA_KEY = "calculator_data";
    private static final String TAG = "CalculatorActivity";
    private TextView tResult;
    private EditText eInputNumber;
    private CalcData calcData;
    private Toast toast;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_layout);
        calcData = new CalcData();
        initViewComponents();
    }

    private void initViewComponents() {
        // кнопки [ 0 ] ... [ 9 ]
        View.OnClickListener buttonNumberClickListener = v -> {
            try {
                String oldValue = eInputNumber.getText().toString();
                if (calcData.isOperatorPressed()) oldValue = "";
                String stNewValue = oldValue + ((Button) v).getText().toString();
                if (stNewValue.equals(".")) stNewValue = "0.";
                Double.parseDouble(stNewValue);// проверка на число
                eInputNumber.setText(FormatValues.getNumberWithoutZerosAtStart(stNewValue));// удаляем нули перед числами
                calcData.setOperatorPressed(false);
            } catch (NumberFormatException e) {
                Log.e(TAG, e.getMessage());
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(v.getContext(), "Некорректное число", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        // кнопки [ + ], [ - ], [ / ], [ * ]
        View.OnClickListener buttonOperatorClickListener = v -> {
            try {
                String operator = ((Button) v).getText().toString();
                calcData.setNumber(Double.valueOf(eInputNumber.getText().toString()));
                calcData.setOperator(operator);
                String infoText = FormatValues.getNumberWithoutZerosAtEnd(calcData.getNumber1().toString()) + operator;
                tResult.setText(infoText);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        Button bClear = findViewById(R.id.button_clear);
        Button b1 = findViewById(R.id.button_1);
        Button b2 = findViewById(R.id.button_2);
        Button b3 = findViewById(R.id.button_3);
        Button b4 = findViewById(R.id.button_4);
        Button b5 = findViewById(R.id.button_5);
        Button b6 = findViewById(R.id.button_6);
        Button b7 = findViewById(R.id.button_7);
        Button b8 = findViewById(R.id.button_8);
        Button b9 = findViewById(R.id.button_9);
        Button b0 = findViewById(R.id.button_0);
        Button bDot = findViewById(R.id.button_dot);
        Button bAdd = findViewById(R.id.button_add);
        Button bSubstract = findViewById(R.id.button_substract);
        Button bMultiply = findViewById(R.id.button_multiply);
        Button bDivide = findViewById(R.id.button_divide);
        Button bDelete = findViewById(R.id.button_delete);
        Button bResult = findViewById(R.id.button_result);
        tResult = findViewById(R.id.textViewResult);
        eInputNumber = findViewById(R.id.editTextInputNumber);
        // set listeners
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
        bAdd.setOnClickListener(buttonOperatorClickListener);
        bSubstract.setOnClickListener(buttonOperatorClickListener);
        bDivide.setOnClickListener(buttonOperatorClickListener);
        bMultiply.setOnClickListener(buttonOperatorClickListener);

        // кнопка [ C ]
        bClear.setOnClickListener(v -> {
            tResult.setText("");
            eInputNumber.setText("0");
            calcData.clear();
        });

        // кнопка [ <| ]
        bDelete.setOnClickListener(v -> {
            String s = eInputNumber.getText().toString();
            if (s.length() > 0) s = s.substring(0, s.length() - 1);
            if (s.length() == 0 || s.equals("-")) s = "0";
            eInputNumber.setText(s);
        });

        // кнопка [ = ]
        bResult.setOnClickListener(v -> {
            try {
                calcData.setNumber(Double.valueOf(eInputNumber.getText().toString()));
                String resultString = "";
                if (calcData.getNumber1() != null)
                    resultString += FormatValues.getNumberWithoutZerosAtEnd(calcData.getNumber1().toString());
                if (!calcData.getOperator().equals("")) {
                    resultString += calcData.getOperator();
                    if (calcData.getNumber2() != null) {
                        calcData.countResult();
                        if (calcData.getResult().toString().equals("Infinity")) {// деление на ноль
                            resultString = "Деление на ноль запрещено";
                        } else {
                            resultString += FormatValues.getNumberWithoutZerosAtEnd(calcData.getNumber2().toString()) + "=";
                            calcData.setNumber1(calcData.getResult());
                            eInputNumber.setText(FormatValues.getNumberWithoutZerosAtEnd(calcData.getResult().toString()));
                        }
                    }
                }
                tResult.setText(resultString);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //    calcData.setInputNumberString(eInputNumber.getText().toString());
        outState.putParcelable(CALC_DATA_KEY, calcData);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calcData = savedInstanceState.getParcelable(CALC_DATA_KEY);
        //      eInputNumber.setText(calcData.getInputNumberString());
    }
}