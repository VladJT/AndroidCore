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

import java.text.DecimalFormat;

import jt.projects.androidcore.R;

public class CalculatorActivity extends AppCompatActivity {

    private final static String CALC_DATA_KEY = "calculator_data";
    private static final String TAG = "CalculatorActivity";

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
    private Button bDelete;
    private Button bAdd;
    private Button bSubstract;
    private Button bDivide;
    private Button bMultiply;
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

                if (calcData.isNeedClearInputNumberString()) oldValue = "";

                String st = oldValue + ((Button) v).getText().toString();
                Double.parseDouble(st);// проверка на число
                eInputNumber.setText(st.replaceAll("^[0]+([1-9])", "$1"));// удаляем нули перед числами
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

                tResult.setText(calcData.getResultInfoString());

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
        tResult = findViewById(R.id.textViewResult);
        eInputNumber = findViewById(R.id.editTextInputNumber);
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
            eInputNumber.setText("");
            calcData.clear();
        });

        // кнопка [ <| ]
        bDelete.setOnClickListener(v -> {
            String s = eInputNumber.getText().toString();
            if (s.length() > 0) s = s.substring(0, s.length() - 1);
            if (s.length() == 0) s = "0";
            eInputNumber.setText(s);
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        calcData.setInputNumberString(eInputNumber.getText().toString());
        outState.putParcelable(CALC_DATA_KEY, calcData);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calcData = savedInstanceState.getParcelable(CALC_DATA_KEY);
        eInputNumber.setText(calcData.getInputNumberString());
    }
}