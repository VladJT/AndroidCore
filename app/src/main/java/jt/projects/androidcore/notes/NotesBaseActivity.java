package jt.projects.androidcore.notes;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class NotesBaseActivity extends AppCompatActivity {
    private static NotesData notesData = null;

    public static NotesData getNotesData() {
        return notesData;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();// отключаем AppBar
        if (notesData == null) {
            notesData = new NotesData();
            notesData.loadData();
        }
        NotesSharedPreferences.initSharedPreferences(getApplicationContext());
        setTheme(NotesSharedPreferences.getAppTheme());
    }
}