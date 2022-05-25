package jt.projects.androidcore.notes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import jt.projects.androidcore.R;

public class NotesBaseActivity extends AppCompatActivity {
    private static final String TAG = "NotesActivity"; // log
    private static NotesData notesData = null;
    private Toast toast;

    public static NotesData getNotesData() {
        return notesData;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NotesTheme);
        getSupportActionBar().hide();// отключаем AppBar
        if (notesData == null) {
            notesData = new NotesData();
            notesData.loadData();
        }
    }


    protected void showLogMessage(Context c, String message) {
        Log.e(TAG, message);
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(c, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
