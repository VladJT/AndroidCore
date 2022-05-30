package jt.projects.androidcore.notes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import jt.projects.androidcore.R;

public class SettingsFragment extends Fragment {
    static final int GALLERY_REQUEST = 1;

    private TextInputEditText itAccountName;
    private MaterialButton btnSaveAccountName;
    private MaterialButton btnChangeAccountPhoto;
    private ImageView ivAccountPhoto;
    private String encodedBitmapPhoto;
    Bitmap bitmapPhoto = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        menu.findItem(R.id.action_settings).setVisible(false);
//        menu.findItem(R.id.action_about).setVisible(false);
        menu.findItem(R.id.action_back).setVisible(true);
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
        final FragmentManager fragmentManager =
                requireActivity().getSupportFragmentManager();
        final List<Fragment> fragments = fragmentManager.getFragments();
        String stFragments = "Список активных фрагментов:\n";
        for (Fragment fragment : fragments) {
            stFragments += fragment.toString() + "\n";
        }
        TextView t = view.findViewById(R.id.notes_info_settings);
        t.setText(stFragments);

        ivAccountPhoto = view.findViewById(R.id.image_view_notes_user_account_photo);
        encodedBitmapPhoto = NotesSharedPreferences.getUserPhotoUriString();
        if (!encodedBitmapPhoto.equals("")) {
            try {
                Bitmap image = NotesSharedPreferences.decodeBase64(encodedBitmapPhoto);
                ivAccountPhoto.setImageBitmap(image);
            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }

        itAccountName = view.findViewById(R.id.notes_account_name);
        itAccountName.setText(NotesSharedPreferences.getUserAccountName());

        initButtonSaveAccountName(view);
        initChangeAccountPhoto(view);
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
            Uri selectedImageUri = data.getData();
            try {
                bitmapPhoto = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                ivAccountPhoto.setImageBitmap(bitmapPhoto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initButtonSaveAccountName(View view) {
        btnSaveAccountName = view.findViewById(R.id.button_notes_account_save);
        btnSaveAccountName.setOnClickListener(v -> {
            // сохраняем аватарку
            if (bitmapPhoto != null) {
                NotesSharedPreferences.saveUserPhotoUriString(NotesSharedPreferences.encodeTobase64(bitmapPhoto));
            }
            // сохраняем имя пользователя
            NotesSharedPreferences.saveUserAccountName(itAccountName.getText() + "");
            Toast toast = Toast.makeText(requireContext(), "Настройки сохранены", Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}
