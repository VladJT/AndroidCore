package jt.projects.androidcore.notes;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import jt.projects.androidcore.notes.data.NotesData;


public class NotesBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotesSharedPreferences.getInstance().
                initSharedPreferences(getApplicationContext());
        setTheme(NotesSharedPreferences.getInstance().getAppTheme());
        NotesData.getInstance().loadData();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        Toast toast = Toast.makeText(getApplicationContext(), "Данные сохранены в " + NotesData.getInstance().getSourceType(), Toast.LENGTH_SHORT);
//        toast.show();
        // если выбран источник данных shared_pref, сохраняем данные при выходе из приложения
        // для firebase - сохранение происходит непосредственно при операциях
        NotesData.getInstance().saveData();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotesData.getInstance().saveData();
    }
}