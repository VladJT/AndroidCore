package jt.projects.androidcore.notes;


import static jt.projects.androidcore.notes.NotesConstants.*;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

import jt.projects.androidcore.R;
import jt.projects.androidcore.common.ConfigInfo;


public class NoteInfoFragment extends Fragment {
    private int currentNoteIndex = -1;// -1 для добавления, остальные для изменения записей
    private TextInputEditText etTopic;
    private TextInputEditText etDescription;
    private TextInputEditText etAuthor;
    private DatePicker dateOfCreation;
    private MaterialButton buttonSaveNote;

    public static NoteInfoFragment newInstance(int index) {
        NoteInfoFragment noteInfoFragment = new NoteInfoFragment();
        Bundle b = new Bundle();
        b.putInt(CURRENT_NOTE_INDEX, index);   // Передача параметра индекс заметки через бандл
        noteInfoFragment.setArguments(b);
        return noteInfoFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_note_info, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(false);
        menu.findItem(R.id.action_back).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_note) {
            saveNote();
        }
        if (id == R.id.action_delete_note) {
            deleteNote();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);// эта строчка говорит о том, что у фрагмента должен быть доступ к меню Активити
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle("Информация о заметке");
        }
        return inflater.inflate(R.layout.fragment_note_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        etTopic = view.findViewById(R.id.notes_info_topic);
        etDescription = view.findViewById(R.id.notes_info_description);
        etAuthor = view.findViewById(R.id.notes_info_author);
        dateOfCreation = view.findViewById(R.id.notes_info_date_of_creation);
        buttonSaveNote = view.findViewById(R.id.notes_info_button_save);
        initButtonSave();

        if (args != null) {
            currentNoteIndex = args.getInt(CURRENT_NOTE_INDEX);
            NotesData.Note currentNote = NotesBaseActivity.getNotesData().getNote(currentNoteIndex);
            etTopic.setText(currentNote.getTopic());
            etDescription.setText(currentNote.getDescription());
            etAuthor.setText(currentNote.getAuthor());
            dateOfCreation.init(currentNote.getDateOfCreation().get(Calendar.YEAR),
                    currentNote.getDateOfCreation().get(Calendar.MONTH),
                    currentNote.getDateOfCreation().get(Calendar.DAY_OF_MONTH),
                    null);
        }
    }

    private void initButtonSave() {
        buttonSaveNote.setOnClickListener(v -> {
            saveNote();
        });
    }

    private void saveNote() {
        NotesData.Note newNote = new NotesData.Note(etTopic.getText().toString(),
                etDescription.getText().toString(),
                etAuthor.getText().toString(),
                new GregorianCalendar(dateOfCreation.getYear(),
                        dateOfCreation.getMonth(), dateOfCreation.getDayOfMonth()));

        if (currentNoteIndex == -1) {
            NotesMainActivity.getNotesData().addNote(newNote);// добавить заметку
        } else if (currentNoteIndex == -2) {
            NotesMainActivity.getNotesData().deleteNote(currentNoteIndex);// добавить заметку
        } else {
            NotesMainActivity.getNotesData().editNote(newNote, currentNoteIndex); // отредактировать заметку
        }
        // уведомляем подписчиков о событии - сохранение заметки
        setResult();
    }

    private void deleteNote() {
        final View customDialogView = getLayoutInflater().inflate(R.layout.dialog_delete_note,null);

        Context c = requireContext();
        new AlertDialog.Builder(c)
                .setTitle("Подтверждение операции")
                .setMessage("Удалить заметку?")
                .setView(customDialogView)
                .setIcon(android.R.drawable.ic_menu_delete)
                .setNeutralButton(c.getText(R.string.no), null)
                .setPositiveButton(c.getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotesMainActivity.getNotesData().deleteNote(currentNoteIndex);// удалить заметку
                        setResult();
                    }
                })
                .show();
    }

    private void setResult() {
        Bundle result = new Bundle();
        result.putInt(EDITED_NOTE_INDEX, currentNoteIndex);
        getParentFragmentManager().setFragmentResult(FRAGMENT_RESULT_NOTES_DATA, result);
        if (!ConfigInfo.isLandscape(requireContext())) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }


}