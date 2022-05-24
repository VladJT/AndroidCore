package jt.projects.androidcore.cities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import jt.projects.androidcore.R;

import static jt.projects.androidcore.cities.EmblemFragment.ARG_INDEX;

public class EmblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emblem);

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }

        // Если эта activity запускается первый раз (с каждым новым гербом первый раз),
        // то перенаправим параметр фрагменту и запустим фрагмент
        City city = getIntent().getExtras().getParcelable(ARG_INDEX);
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.coat_of_arms_fragment_container,
                            EmblemFragment.newInstance(city)).commit();
    }


}