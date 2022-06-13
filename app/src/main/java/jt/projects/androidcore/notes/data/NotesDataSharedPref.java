package jt.projects.androidcore.notes.data;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import jt.projects.androidcore.notes.NotesSharedPreferences;
import jt.projects.androidcore.notes.constants.NotesConstants;

public class NotesDataSharedPref extends NotesData {


    @Override
    public void loadData() {
        try {
            SharedPreferences sharedPref = NotesSharedPreferences.getInstance().getCustomSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES_DATA);
            String jsonNotes = sharedPref.getString(NotesConstants.NOTES_JSON_DATA, null);
            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();

            this.data = new GsonBuilder().create().fromJson(jsonNotes, type);
            if (data == null) {
                data = new ArrayList<>();
            }
            sortByDate(data);

        } catch (JsonSyntaxException e) {
            //    Toast.makeText(this, "Ошибка трансформации", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void saveData() {
        SharedPreferences sharedPref = NotesSharedPreferences.getInstance().getCustomSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES_DATA);
        String jsonNotes = "";
        if (data != null) {
            jsonNotes = new GsonBuilder().create().toJson(data);
        }
        sharedPref.edit().putString(NotesConstants.NOTES_JSON_DATA, jsonNotes).apply();
    }
}
