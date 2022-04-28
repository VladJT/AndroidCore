package jt.projects.androidcore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Основные файлы в проекте:
 * 1. Класс MainActivity.java в разделе java\ru.geekbrains.hellogeekbrains. Это активити проекта,
 * класс обеспечивает работу жизненного цикла активити. Активити связана с макетом (Layout).
 * 2. Макет (Layout, лейаут) находится в файле activity_main.xml по пути res\layout. Это форма
 * расположения элементов на экране.
 * 3. Манифест (Manifest) декларирует структуру проекта, его права, пиктограмму, тему
 * приложения, компоненты приложения, прочие метаданные, необходимые для системы
 * Android, чтобы управлять этим приложением. Находится в файле AndroidManifest.xml в папке
 * manifests.
 * 4. Ресурсы приложения находятся в папке res. Это строки, изображения, стили, сюда же можно
 * отнести и макеты.
 */

public class MainActivity extends AppCompatActivity {

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


        gb_1();
        // 2
        //  setContentView(R.layout.ex_nested_scroll_view);
        // 3
        setContentView(R.layout.linear_example_layout);
//        loadImageFromAsset(findViewById(R.id.imageViewTest), "pictures/nature.jpg");

        initList();
    }

    private void initList() {
        LinearLayout layoutList = findViewById(R.id.LinearLayoutTest);
        String[] versions = getResources().getStringArray(R.array.version_names);

        LayoutInflater ltInflater = getLayoutInflater();
        TypedArray imgs =
                getResources().obtainTypedArray(R.array.version_logos);
        for (int i = 0; i < versions.length; i++) {
            String version = versions[i];
// Достаём элемент из android_item.xml
            View item = ltInflater.inflate(R.layout.versions, layoutList,
                    false);
// Находим в этом элементе TextView
            TextView tv = item.findViewById(R.id.text1);
            tv.setText(version);
// Получить из ресурсов массив указателей на изображения
// Выбрать по индексу подходящее изображение
            AppCompatImageView imgLogo = item.findViewById(R.id.imgTest1);
            imgLogo.setImageResource(imgs.getResourceId(i, -1));
            layoutList.addView(item);
        }
    }


    public void loadImageFromAsset(ImageView image, String fileName) {
        try {
            InputStream ims = getAssets().open(fileName);
            // загружаем как Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // выводим картинку в ImageView
            image.setImageDrawable(d);
        } catch (Exception ex) {
            return;
        }
    }


    private void gb_1() {
        //  setContentView(R.layout.activity_main);
        //   setListeners();
    }


    private void initComponents() {
        mySwitch = findViewById(R.id.switch1);
        button = findViewById(R.id.button);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
        checkBox = findViewById(R.id.checkBox);
        calendarView = findViewById(R.id.calendarView);
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
                        textView.setText("Числа равны");
                    } else {
                        textView.setText("Числа НЕ равны!");
                    }
                } catch (Exception e) {
                    textView.setText("Введите число");
                }
            }
        });

        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mySwitch.isChecked())
//                    Toast.makeText(v.getContext(), "Checked EQUALS", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(v.getContext(), "Unchecked EQUALS (use =)", Toast.LENGTH_SHORT).show();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setText(checkBox.isChecked() + "");
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