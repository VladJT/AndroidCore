package jt.projects.androidcore.notes;

import static jt.projects.androidcore.notes.data.NotesConstants.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import jt.projects.androidcore.R;
import jt.projects.androidcore.notes.data.NotesData;
import jt.projects.androidcore.notes.data.RESULT_EDIT_NOTE;
import jt.projects.androidcore.notes.data.IFBResponse;


public class NotesListFragment extends Fragment {
    private static final int MY_DEFAULT_DURATION = 300;
    //    private static final String CURRENT_NOTE = "CurrentNote";
    private MaterialButton buttonAddNote;
    private RecyclerView notesRecyclerView;
    private NotesListAdapter notesListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNoteInfoChangeListener();
        NotesData.getInstance().setResponse(new IFBResponse() {
            @Override
            public void initialized() {
                try {
                    if (notesListAdapter != null) {
                        notesListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast toast = Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    // обработчик события редактирования/удаления заметки
    private void setNoteInfoChangeListener() {
        getParentFragmentManager().setFragmentResultListener(FRAGMENT_RESULT_NOTES_DATA, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                int index = bundle.getInt(EDITED_NOTE_INDEX);
                RESULT_EDIT_NOTE resultEditNote = (RESULT_EDIT_NOTE) bundle.getSerializable(RESULT_EDIT);
                String noteTopic = NotesData.getInstance().getNote(index).getTopic();

                if (resultEditNote == RESULT_EDIT_NOTE.ADD) {// если добавлена новая заметка
                    notesListAdapter.notifyItemInserted(index);
                    notesRecyclerView.scrollToPosition(index);
                    Snackbar.make(requireActivity().findViewById(R.id.notes_list_recycler_view), "Добавлена заметка: " + noteTopic, Snackbar.LENGTH_SHORT).show();
                } else if (resultEditNote == RESULT_EDIT_NOTE.EDIT) {// если заметка отредактирована
                    notesListAdapter.notifyItemChanged(index);
                    notesRecyclerView.scrollToPosition(index);
                    Snackbar.make(requireActivity().findViewById(R.id.notes_list_recycler_view), "Отредактирована заметка: " + noteTopic, Snackbar.LENGTH_SHORT).show();
                } else if (resultEditNote == RESULT_EDIT_NOTE.DELETE) {// если заметка удалена
                    notesListAdapter.notifyItemRemoved(index);
                    Snackbar.make(requireActivity().findViewById(R.id.notes_list_recycler_view), "Удалена заметка: " + noteTopic, Snackbar.LENGTH_SHORT).show();
                    NotesData.getInstance().deleteNote(index);
                    int scrollIndex = index - 1;
                    if (scrollIndex >= 0) notesRecyclerView.smoothScrollToPosition(scrollIndex);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.findItem(R.id.action_back).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);// эта строчка говорит о том, что у фрагмента должен быть доступ к меню Активити
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle("Список заметок (" + NotesSharedPreferences.getInstance().getDBSource() + ")");
        }

        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        notesRecyclerView = view.findViewById(R.id.notes_list_recycler_view);
        initRecyclerView(notesRecyclerView);
        return view;
    }

    private void initRecyclerView(RecyclerView notesRecyclerView) {
        // Эта установка служит для повышения производительности системы
        notesRecyclerView.setHasFixedSize(true);
        // Будем работать со встроенным менеджером
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Установим адаптер
        notesListAdapter = new NotesListAdapter(NotesData.getInstance(), this);
        notesRecyclerView.setAdapter(notesListAdapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        notesRecyclerView.addItemDecoration(itemDecoration);

        // Установим анимацию. А чтобы было хорошо заметно, сделаем анимацию долгой
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setChangeDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        animator.setMoveDuration(MY_DEFAULT_DURATION);
        notesRecyclerView.setItemAnimator(animator);

        // Установим слушателя
        notesListAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNoteInfo(notesListAdapter.getMenuPosition());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            //    currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }
        initButtonAdd(view);
    }

    private void initButtonAdd(@NonNull View view) {
        buttonAddNote = view.findViewById(R.id.notes_button_add);
        buttonAddNote.setOnClickListener(v -> {
            showNoteInfo(-1);
        });

        // POPUP MENU
        buttonAddNote.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
            requireActivity().getMenuInflater().inflate(R.menu.notes_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> checkMenuItemSelected(item.getItemId()));
            popupMenu.show();
            return false;
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.notes_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        checkMenuItemSelected(item.getItemId());
        return super.onContextItemSelected(item);
    }

    private boolean checkMenuItemSelected(int id) {
        switch (id) {
            case R.id.action_add_note:
                showNoteInfo(-1);
                return true;
            case R.id.action_edit_note:
                showNoteInfo(notesListAdapter.getMenuPosition());
                return true;
            case R.id.action_delete_note:
                setResult(RESULT_EDIT_NOTE.DELETE);
                return true;
            case R.id.action_settings:
                showFragment(new SettingsFragment());
                return true;
            case R.id.action_about:
                showFragment(new AboutFragment());
                return true;
        }
        return false;
    }

    private void showNoteInfo(int noteIndex) {
        showFragment(NoteInfoFragment.newInstance(noteIndex));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.notes_list_fragment_container, fragment);
        for (Fragment f : requireActivity().getSupportFragmentManager().getFragments()) {
            if (f instanceof NotesListFragment & f.isVisible()) {
                ft.addToBackStack("");
                break;
            }
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private void setResult(RESULT_EDIT_NOTE result) {
        Bundle bundle = new Bundle();
        bundle.putInt(EDITED_NOTE_INDEX, notesListAdapter.getMenuPosition());
        bundle.putSerializable(RESULT_EDIT, result);
        getParentFragmentManager().setFragmentResult(FRAGMENT_RESULT_NOTES_DATA, bundle);
    }
}