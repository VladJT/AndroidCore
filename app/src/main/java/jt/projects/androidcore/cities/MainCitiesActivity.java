package jt.projects.androidcore.cities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import jt.projects.androidcore.R;

public class MainCitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CalcLightTheme);
        setContentView(R.layout.activity_main_cities);

        // Создаем фрагмент
        CitiesFragment cf = new CitiesFragment();
        // Чтобы вставить фрагмент, надо получить «Менеджер фрагментов», затем открыть
        //транзакцию, вставить макет и закрыть транзакцию

        // Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        //пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, cf)
                    .commit();
        }
    }
}