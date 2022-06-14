package jt.projects.androidcore.notes.data;

import android.widget.Toast;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.datastore.rxjava3.RxDataStoreBuilder;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import jt.projects.androidcore.notes.NotesSharedPreferences;
import jt.projects.androidcore.notes.constants.NotesConstants;

public class NotesDataStore extends NotesData {
    static RxDataStore<Preferences> dataStore =
            new RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build();
    //Каждый ключ указывает на тип хранимых в нем данных и строковый ключ, по которому эти данные будут читаться
    Preferences.Key<String> DATA_KEY = PreferencesKeys.stringKey("json_data");

    @Override
    public void loadData() {
        try {
            //Flowable<String> dataFlow = dataStore.data().map(prefs -> prefs.get(DATA_KEY));
            String jsonNotes = dataStore.data().blockingFirst().get(DATA_KEY);

            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();
            this.data = new GsonBuilder().create().fromJson(jsonNotes, type);
            if (data == null) {
                data = new ArrayList<>();
            }
            sortByDate(data);
        } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void saveData() {
        String jsonNotes = "";
        if (data != null) {
            jsonNotes = new GsonBuilder().create().toJson(data);
        }
        final String st = jsonNotes;
        Single<Preferences> updateResult = dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(DATA_KEY, st);
            return Single.just(mutablePreferences);
        });
    }
}
