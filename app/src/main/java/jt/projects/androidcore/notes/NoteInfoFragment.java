package jt.projects.androidcore.notes;


import static jt.projects.androidcore.notes.constants.NotesConstants.*;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import jt.projects.androidcore.R;
import jt.projects.androidcore.notes.data.Note;
import jt.projects.androidcore.notes.data.NotesData;
import jt.projects.androidcore.notes.constants.RESULT_EDIT_NOTE;


public class NoteInfoFragment extends Fragment {
    private int currentNoteIndex = -1;// -1 для добавления, остальные для изменения записей
    private TextInputEditText etTopic;
    private TextInputEditText etDescription;
    private TextInputEditText etAuthor;
    private MaterialButton buttonSaveNote;
    private Button buttonEditDateOfCreation;
    private Note currentNote;
    private Calendar newDateOfCreation = Calendar.getInstance();

    public static NoteInfoFragment newInstance(int index) {
        NoteInfoFragment noteInfoFragment = new NoteInfoFragment();
        Bundle b = new Bundle();
        b.putInt(CURRENT_NOTE_INDEX, index);   // Передача параметра индекс заметки через бандл
        noteInfoFragment.setArguments(b);
        return noteInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null)
//            requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_note_info, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(false);
        menu.findItem(R.id.action_back).setVisible(true);
        if (currentNoteIndex == -1)// для создания новой заметки - не показываем кнопку удаления
            menu.findItem(R.id.action_delete_note).setVisible(false);
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
        Bundle args = getArguments();
        if (args != null) {
            currentNoteIndex = args.getInt(CURRENT_NOTE_INDEX);
            currentNote = NotesData.getInstance().getNote(currentNoteIndex);
        }
        return inflater.inflate(R.layout.fragment_note_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTopic = view.findViewById(R.id.notes_info_topic);
        etDescription = view.findViewById(R.id.notes_info_description);
        etAuthor = view.findViewById(R.id.notes_info_author);

        initButtonSave(view);
        initButtonEditDateOfCreation(view);

        etTopic.setText(currentNote.getTopic());
        etDescription.setText(currentNote.getDescription());
        etAuthor.setText(currentNote.getAuthor());
        buttonEditDateOfCreation.setText(currentNote.getDateOfCreationAsString());
    }

    private void initButtonSave(@NonNull View view) {
        buttonSaveNote = view.findViewById(R.id.notes_info_button_save);
        buttonSaveNote.setOnClickListener(v -> {
            saveNote();
        });
    }

    private void initButtonEditDateOfCreation(View view) {
        buttonEditDateOfCreation = view.findViewById(R.id.button_notes_info_date_of_creation);
        buttonEditDateOfCreation.setOnClickListener(new View.OnClickListener() {
            // Process to get Current Date
            final Calendar c = currentNote.getDateOfCreation();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(requireContext(),
                        // android.R.style.Theme_Material_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date
                                newDateOfCreation = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                String newStrDate = new SimpleDateFormat("dd.MM.yyyy").format(newDateOfCreation.getTime());
                                buttonEditDateOfCreation.setText(newStrDate);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }

    private void saveNote() {
        Note newNote = new Note(etTopic.getText().toString(),
                etDescription.getText().toString(), etAuthor.getText().toString(), newDateOfCreation);
        if (currentNoteIndex == -1) {
            // добавить заметку
            NotesData.getInstance().addNote(newNote);
            currentNoteIndex = NotesData.getInstance().getSize() - 1;
            setResult(RESULT_EDIT_NOTE.ADD); // уведомляем подписчиков о событии - сохранение заметки
        } else {
            // отредактировать заметку
            NotesData.getInstance().editNote(newNote, currentNoteIndex);
            setResult(RESULT_EDIT_NOTE.EDIT); // уведомляем подписчиков о событии - сохранение заметки
        }
    }


    private void deleteNote() {
        final View customDialogView = getLayoutInflater().inflate(R.layout.dialog_delete_note, null);
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
                        setResult(RESULT_EDIT_NOTE.DELETE);
                    }
                }).show();
    }

    private void setResult(RESULT_EDIT_NOTE result) {
        Bundle bundle = new Bundle();
        bundle.putInt(EDITED_NOTE_INDEX, currentNoteIndex);
        bundle.putSerializable(RESULT_EDIT, result);
        getParentFragmentManager().setFragmentResult(FRAGMENT_RESULT_NOTES_DATA, bundle);
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}