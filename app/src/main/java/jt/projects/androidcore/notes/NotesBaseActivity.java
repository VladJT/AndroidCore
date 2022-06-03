package jt.projects.androidcore.notes;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class NotesBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();// отключаем AppBar
        NotesSharedPreferences.initSharedPreferences(getApplicationContext());
        setTheme(NotesSharedPreferences.getAppTheme());
    }
}