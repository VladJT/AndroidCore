package jt.projects.androidcore.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import jt.projects.androidcore.R;

public class NotesMainActivity extends AppCompatActivity {

    private static NotesData notesData;

    public static NotesData getNotesData() {
        return notesData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesData = new NotesData();
        notesData.loadData();

        setTheme(R.style.CalcLightTheme);
        // отключаем AppBar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_notes);

        NotesListFragment notesListFragment = new NotesListFragment();
        // Чтобы вставить фрагмент, надо получить «Менеджер фрагментов», затем открыть
        //транзакцию, вставить макет и закрыть транзакцию
        // Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        //пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_list_fragment_container, notesListFragment)
                    .commit();
        }
    }
}