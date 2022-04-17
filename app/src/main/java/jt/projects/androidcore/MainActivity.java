package jt.projects.androidcore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    private Integer arg1;
    private Integer arg2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        final EditText editText1 = findViewById(R.id.editText1);
        final EditText editText2 = findViewById(R.id.editText2);
        final TextView textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    arg1 = Integer.valueOf(editText1.getText().toString());
                    arg2 = Integer.valueOf(editText2.getText().toString());
                    if (arg1.equals(arg2)) {
                        textView.setText("Равно!");
                    } else {
                        textView.setText("НЕ Равно!");
                    }
                }
                catch (Exception e){
                    textView.setText("Введите число");
                }
            }
        });
    }

}