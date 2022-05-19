package jt.projects.androidcore.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import jt.projects.androidcore.R;

public class NotesMainActivity extends NotesBaseActivity implements NoteChangePublisherGetter {

    private NoteChangePublisher publisher = new NoteChangePublisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notes);

        NotesListFragment notesListFragment = new NotesListFragment();
        publisher.subscribe(notesListFragment);
        // Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        // пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_list_fragment_container, notesListFragment)
                    .commit();
        }
    }

    @Override
    public NoteChangePublisher getPublisher() {
        return publisher;
    }
}