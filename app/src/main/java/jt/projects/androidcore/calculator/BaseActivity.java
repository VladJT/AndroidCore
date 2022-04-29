package jt.projects.androidcore.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import jt.projects.androidcore.R;

public class BaseActivity extends AppCompatActivity {
    private static final String NameSharedPreference = "GB_THEME";  // Имя настроек
    private static final String appTheme = "APP_THEME";    // Имя параметра в настройках

    protected Map<Integer, Integer> themesWithRbuttons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initThemes();
        setTheme(getAppTheme());
        setContentView(R.layout.calculator_layout);
    }

    private void initThemes() {
        themesWithRbuttons = new HashMap<>();
        themesWithRbuttons.put(0, R.style.CalcDarkTheme);
        themesWithRbuttons.put(1, R.style.CalcLightTheme);
    }

    protected int getAppTheme() {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        int defaultTheme = R.style.CalcDarkTheme;
        return sharedPref.getInt(appTheme, defaultTheme);
    }

    protected void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(appTheme, codeStyle);
        editor.apply();
    }
}