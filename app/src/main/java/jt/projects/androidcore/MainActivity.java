package jt.projects.androidcore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Основные файлы в проекте:
 * 1. Класс MainActivity.java в разделе java\ru.geekbrains.hellogeekbrains. Это активити проекта,
 * класс обеспечивает работу жизненного цикла активити. Активити связана с макетом (Layout).
 * <p>
 * 2. Макет (Layout, лейаут) находится в файле activity_main.xml по пути res\layout. Это форма
 * расположения элементов на экране.
 * <p>
 * 3. Манифест (Manifest) декларирует структуру проекта, его права, пиктограмму, тему
 * приложения, компоненты приложения, прочие метаданные, необходимые для системы
 * Android, чтобы управлять этим приложением. Находится в файле AndroidManifest.xml в папке
 * manifests.
 * <p>
 * 4. Ресурсы приложения находятся в папке res. Это строки, изображения, стили, сюда же можно
 * отнести и макеты.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SwitchCompat mySwitch;
    Button button;
    EditText editText1;
    EditText editText2;
    TextView textView;
    CheckBox checkBox;
    CalendarView calendarView;
    private Toast toast;

    private Integer arg1;
    private Integer arg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setListeners();
    }


    private void initComponents() {
        mySwitch = findViewById(R.id.switch1);
        button = findViewById(R.id.button);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
        checkBox = findViewById(R.id.checkBox);
        calendarView = findViewById(R.id.calendarView);
        Log.i(TAG, "инициализированы view...");
    }


    private void setListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    arg1 = Integer.valueOf(editText1.getText().toString());
                    arg2 = Integer.valueOf(editText2.getText().toString());

                    boolean result;
                    if (mySwitch.isChecked()) result = arg1.equals(arg2);
                    else result = (arg1 == arg2);
                    if (result) {
                        textView.setText("Равно!");
                    } else {
                        textView.setText("НЕ Равно!");
                    }
                } catch (Exception e) {
                    textView.setText("Введите число");
                    Log.e(TAG, "onClick exception: " + e.getMessage());
                }
            }
        });

        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySwitch.isChecked())
                    Toast.makeText(v.getContext(), "Checked EQUALS", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(), "Unchecked EQUALS (use =)", Toast.LENGTH_SHORT).show();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), checkBox.isChecked() + "", Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (toast != null) {
                    toast.cancel();
                }
                DecimalFormat dF = new DecimalFormat("00");
                toast = Toast.makeText(calendarView.getContext(), dF.format(dayOfMonth) + "." + dF.format(month) + "." + year, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }


}