package jt.projects.androidcore.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import jt.projects.androidcore.R;

public class NotesMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CalcLightTheme);
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