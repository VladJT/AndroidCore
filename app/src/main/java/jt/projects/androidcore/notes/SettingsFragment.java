package jt.projects.androidcore.notes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import jt.projects.androidcore.R;
import jt.projects.androidcore.calculator.BaseActivity;

public class SettingsFragment extends Fragment {
    static final int GALLERY_REQUEST = 1;

    private TextInputEditText itAccountName;
    private ProgressBar progressBar;
    private MaterialButton btnSaveAccountName;
    private MaterialButton btnChangeAccountPhoto;
    private MaterialButton btnDeleteAccountPhoto;
    private ImageView ivAccountPhoto;
    private ImageView ivDbSource;
    private String encodedBitmapPhoto;
    private SwitchMaterial switchDbSource;
    Bitmap bitmapPhoto = null;
    boolean switchDbSourceStartChecked;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null)
//            requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_note_info, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(false);
        menu.findItem(R.id.action_delete_note).setVisible(false);
        menu.findItem(R.id.action_back).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_note) {
            saveAccountData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);// эта строчка говорит о том, что у фрагмента должен быть доступ к меню Активити
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle("Настройки приложения");
        }
        return inflater.inflate(R.layout.fragment_notes_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final FragmentManager fragmentManager =
//                requireActivity().getSupportFragmentManager();
//        final List<Fragment> fragments = fragmentManager.getFragments();
//        String stFragments = "Список активных фрагментов:\n";
//        for (Fragment fragment : fragments) {
//            stFragments += fragment.toString() + "\n";
//        }
//        TextView t = view.findViewById(R.id.notes_info_settings);
//        t.setText(stFragments);

        ivAccountPhoto = view.findViewById(R.id.image_view_notes_user_account_photo);
        ivAccountPhoto.setImageBitmap(NotesSharedPreferences.getInstance().getBitmapPhoto());

        itAccountName = view.findViewById(R.id.notes_account_name);
        itAccountName.setText(NotesSharedPreferences.getInstance().getUserAccountName());

        progressBar = view.findViewById(R.id.progress_bar_notes_settings);

        initButtonSaveAccountName(view);
        initChangeAccountPhoto(view);
        initDeletePhoto(view);
        initDbSourceControls(view);
    }

    private void initDbSourceControls(View view) {
        switchDbSource = view.findViewById(R.id.switch_db_source);
        ivDbSource = view.findViewById(R.id.image_view_notes_db_source);

        if ( NotesSharedPreferences.getInstance().getDBSource()== DATABASE.SHARED_PREF){
            switchDbSource.setChecked(true);
        }
        switchDbSourceStartChecked = switchDbSource.isChecked();

        showDbSourceInfo();

        switchDbSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDbSourceInfo();
            }
        });
    }

    private void showDbSourceInfo() {
        if (!switchDbSource.isChecked()) {
            switchDbSource.setText("Firebase");
            switchDbSource.setChecked(false);
            ivDbSource.setImageDrawable(requireActivity().getDrawable(R.drawable.firebase));
        } else {
            switchDbSource.setText("Shared preferences");
            switchDbSource.setChecked(true);
            ivDbSource.setImageDrawable(requireActivity().getDrawable(R.drawable.shared_pref));
        }
    }


    private void initDeletePhoto(View view) {
        btnDeleteAccountPhoto = view.findViewById(R.id.button_notes_delete_account_photo);
        btnDeleteAccountPhoto.setOnClickListener(v -> {
            Snackbar snackbar = Snackbar
                    .make(view, "Удалить фото профиля?", Snackbar.LENGTH_LONG)
                    .setAction(requireContext().getResources().getText(R.string.yes), new View.OnClickListener() {
                        @Override
                        public void onClick(View view1) {
                            bitmapPhoto = null;
                            NotesSharedPreferences.getInstance().saveUserPhotoUriString("");
                            ivAccountPhoto.setImageBitmap(NotesSharedPreferences.getInstance().getBitmapPhoto());
                        }
                    });
            snackbar.show();
        });
    }

    private void initChangeAccountPhoto(View view) {
        btnChangeAccountPhoto = view.findViewById(R.id.button_notes_change_account_photo);
        btnChangeAccountPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    Uri selectedImageUri = data.getData();
                    bitmapPhoto = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                    ivAccountPhoto.setImageBitmap(bitmapPhoto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initButtonSaveAccountName(View view) {
        btnSaveAccountName = view.findViewById(R.id.button_notes_account_save);
        btnSaveAccountName.setOnClickListener(v -> {
            saveAccountData();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void saveAccountData() {
        StringBuilder result = new StringBuilder();
        try {
            //progressBar.setVisibility(View.VISIBLE);
            Thread threadLoadPhoto = new Thread(() -> {
                // сохраняем аватарку
                if (bitmapPhoto != null) {
                    NotesSharedPreferences.getInstance()
                            .saveUserPhotoUriString(NotesSharedPreferences.getInstance().encodeTobase64(bitmapPhoto));
                    progressBar.setVisibility(View.GONE);
                    result.append("Изменено фото\n");
                }
            });
            threadLoadPhoto.start();
            threadLoadPhoto.join();

            // сохраняем имя пользователя
            if (!(itAccountName.getText() + "").equals(NotesSharedPreferences.getInstance().getUserAccountName())) {
                NotesSharedPreferences.getInstance().saveUserAccountName(itAccountName.getText() + "");
                result.append("Изменен nickname\n");
            }

            //при необходимости меняем источник данных
            if (switchDbSource.isChecked() != switchDbSourceStartChecked) {
                NotesData.getInstance().saveData();
                NotesSharedPreferences.getInstance().saveDBSource(switchDbSource.isChecked() ? DATABASE.SHARED_PREF : DATABASE.FIREBASE);
                result.append("Изменен источник данных: " + NotesSharedPreferences.getInstance().getDBSource().name() + "\n");
                NotesData.getInstance().loadData();
            }

            Snackbar.make(requireActivity().findViewById(R.id.image_view_notes_user_account_photo), result.toString().equals("") ? "Нет изменений" : result.toString(), Snackbar.LENGTH_LONG).show();
            switchDbSourceStartChecked = switchDbSource.isChecked();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}