package jt.projects.androidcore.calculator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import jt.projects.androidcore.R;

public class BaseActivity extends AppCompatActivity {
    private static final String NameSharedPreference = "GB_THEME";  // Имя настроек
    private static final String appTheme = "APP_THEME";    // Имя параметра в настройках
    private static final String TAG = "CalculatorActivity"; // log

    private Toast toast;
    protected Map<Integer, Integer> themesMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initThemes();
        setTheme(getAppTheme());
        setContentView(R.layout.calculator_layout);
    }

    private void initThemes() {
        themesMap = new HashMap<>();
        themesMap.put(0, R.style.CalcDarkTheme);
        themesMap.put(1, R.style.CalcLightTheme);
        themesMap.put(2, R.style.CalcRedTheme);
    }

    protected int getAppTheme() {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        int defaultTheme = R.style.CalcDarkTheme;
        return sharedPref.getInt(appTheme, defaultTheme);
    }

    public int getCurrentAppThemeIndex() {
        for (Map.Entry<Integer, Integer> k : themesMap.entrySet()) {
            if (k.getValue() == getAppTheme()) {
                return k.getKey();
            }
        }
        return 0;
    }

    protected void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(appTheme, codeStyle);
        editor.apply();
    }


//    void showThemeChooseAlertDialog() {
//        String[] singleChoiceItems = getResources().getStringArray(R.array.calc_themes);
//        final int[] itemSelected = {0};
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setTitle("Выберите тему");
//
//        alertDialog.setSingleChoiceItems(singleChoiceItems, itemSelected[0], new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
//                itemSelected[0] = selectedIndex;
//            }
//        });
//        alertDialog.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                setAppTheme(themesMap.get(itemSelected[0]));
//                recreate();
//            }
//        });
//        alertDialog.setNegativeButton(getString(R.string.Cancel), null);
//        alertDialog.create().show();
//    }


    protected void showLogMessage(Context c, String message) {
        Log.e(TAG, message);
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(c, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}