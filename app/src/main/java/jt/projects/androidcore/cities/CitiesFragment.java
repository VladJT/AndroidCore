package jt.projects.androidcore.cities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jt.projects.androidcore.R;

import static jt.projects.androidcore.cities.EmblemFragment.ARG_INDEX;


public class CitiesFragment extends Fragment {
    private static final String CURRENT_CITY = "CurrentCity";
    // Текущая позиция (выбранный город)
    private City city = null;
    // private int currentPosition = 0;

    public CitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    // Этот метод вызывается, когда макет экрана создан и готов к отображению
    // информации. Создаем список городов
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            city = savedInstanceState.getParcelable(CURRENT_CITY);
        }
        initList(view);
        if (isLandscape()) {
            showLandscapeEmblem(city);
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }


    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] cities = getResources().getStringArray(R.array.cities);
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        for (int i = 0; i < cities.length; i++) {
            String currentCity = cities[i];
            TextView tw = new TextView(getContext());
            tw.setText(currentCity);
            tw.setTextSize(30);
            layoutView.addView(tw);
            final int pos = i;
            tw.setOnClickListener(v -> {
                city = new City(pos, currentCity);
                showEmblem(city);
            });
        }
    }

    private void showEmblem(City city) {
        if (isLandscape()) {
            showLandscapeEmblem(city);
        } else showPortraitEmblem(city);
    }

    private void showLandscapeEmblem(City city) {
        EmblemFragment ef = EmblemFragment.newInstance(city);
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.emblem_container, ef);
        ft.addToBackStack("");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private void showPortraitEmblem(City city) {
//        Activity a = requireActivity();
//        final Intent intent = new Intent(a, EmblemActivity.class);
//        intent.putExtra(ARG_INDEX, city);
//        a.startActivity(intent);


        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        // обратите внимание на метод транзакции add.
        // Теперь мы не заменяем, а добавляем фрагмент, чтобы он открывался поверх предыдущего.
        ft.add(R.id.fragment_container, EmblemFragment.newInstance(city));
        //Чтобы добавить фрагмент в очередь
        ft.addToBackStack("");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_CITY, city);
        super.onSaveInstanceState(outState);
    }
}