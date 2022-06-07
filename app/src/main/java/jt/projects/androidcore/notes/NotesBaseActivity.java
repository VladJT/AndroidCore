package jt.projects.androidcore.notes;


import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class NotesBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();// отключаем AppBar
        NotesSharedPreferences.getInstance().
                initSharedPreferences(getApplicationContext());
        setTheme(NotesSharedPreferences.getInstance().getAppTheme());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Toast toast = Toast.makeText(getApplicationContext(), "Данные сохранены в " + NotesData.getInstance().sourceType, Toast.LENGTH_SHORT);
        toast.show();
        NotesData.getInstance().saveData();
        super.onSaveInstanceState(outState);
    }
}