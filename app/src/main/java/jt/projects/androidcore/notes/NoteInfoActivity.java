package jt.projects.androidcore.notes;


import android.content.res.Configuration;
import android.os.Bundle;

import jt.projects.androidcore.R;


public class NoteInfoActivity extends NotesBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }

        // Если эта activity запускается первый раз (т.е. пересоздается для каждой новой заметки)
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_info_fragment_container,
                            NoteInfoFragment.newInstance(getIntent().getExtras().getInt(NoteInfoFragment.CURRENT_NOTE_INDEX)))
                    .commit();
    }

}