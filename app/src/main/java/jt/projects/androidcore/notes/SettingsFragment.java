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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import jt.projects.androidcore.R;
import jt.projects.androidcore.notes.common.DownloadImageTask;
import jt.projects.androidcore.notes.common.IDownloadListener;
import jt.projects.androidcore.notes.constants.DATABASE;
import jt.projects.androidcore.notes.data.NotesData;

public class SettingsFragment extends Fragment {
    static final int GALLERY_REQUEST = 1;

    private TextInputEditText itAccountName;
    private ProgressBar progressBar;
    private MaterialButton btnSaveSettings;
    private MaterialButton btnChangeAccountPhoto;
    private MaterialButton btnDeleteAccountPhoto;
    private ImageView ivAccountPhoto;
    private TextView tvInfo;
    private RadioGroup rgDbSource;
    private RadioButton rbSharedPref;
    private RadioButton rbFirebase;
    private RadioButton rbDataStore;

    private Bitmap bitmapPhoto = null;
    private boolean isPhotoChanged = false;
    private int startDbSourceChecked;

    // for google auth
    private static final int RC_SIGN_IN = 40404;// Используется, чтобы определить результат activity регистрации через Google
    private GoogleSignInClient googleSignInClient;// Клиент для регистрации пользователя через Google
    private SignInButton btnSignIn;// Кнопка регистрации через Google
    private MaterialButton btnSignOut;// Кнопка выхода из Google
    private GoogleSignInAccount accountGoogle;

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

        tvInfo = view.findViewById(R.id.notes_info_settings);
        ivAccountPhoto = view.findViewById(R.id.image_view_notes_user_account_photo);
        itAccountName = view.findViewById(R.id.notes_account_name);
        progressBar = view.findViewById(R.id.progress_bar_notes_settings);

        initButtonSaveSettings(view);
        initChangeAccountPhoto(view);
        initDeletePhoto(view);
        initDbSourceControls(view);

        initGoogleSign();
        initGoogleView(view);
        updateUI();
    }

    // Инициализация запроса на аутентификацию
    private void initGoogleSign() {
        // Конфигурация запроса на регистрацию пользователя, чтобы получить
        // идентификатор пользователя, его почту и основной профайл
        // (регулируется параметром)
        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Получаем клиента для регистрации и данные по клиенту
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void initGoogleView(View view) {
        // кнопка входа
        btnSignIn = view.findViewById(R.id.button_sign_in_google);
        btnSignIn.setOnClickListener(v -> signIn());

        // Кнопка выхода
        btnSignOut = view.findViewById(R.id.button_sign_out_google);
        btnSignOut.setOnClickListener(v -> signOut());

        // Проверим, входил ли пользователь в это приложение через Google
        accountGoogle = GoogleSignIn.getLastSignedInAccount(requireContext());
    }

    // Инициируем регистрацию пользователя
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Выход из учётной записи в приложении
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        accountGoogle = null;
                        NotesSharedPreferences.getInstance().saveUserPhotoToAssets(NotesSharedPreferences.getInstance().getEmptyPhoto());
                        NotesSharedPreferences.getInstance().saveUserAccountName("user");
                        updateUI();
                        Snackbar.make(requireActivity().findViewById(R.id.image_view_notes_user_account_photo),
                                "Выполнен выход из уч.записи Google", Snackbar.LENGTH_LONG).show();

                    }
                });
    }


    private void updateUI() {
        if (accountGoogle != null) {
            itAccountName.setText(accountGoogle.getDisplayName());
            IDownloadListener downloadListener = new IDownloadListener() {
                @Override
                public void onDownloadComplete(Bitmap bitmap) {
                    ivAccountPhoto.setImageBitmap(bitmap);
                    bitmapPhoto = bitmap;
                    // сравним картинки
                    if (!NotesSharedPreferences.getInstance().getBitmapPhoto().sameAs(bitmap)) {
                        saveAccountData();
                    }
                }
            };
            DownloadImageTask googlePhoto = new DownloadImageTask(downloadListener);
            googlePhoto.execute(accountGoogle.getPhotoUrl().toString());
            tvInfo.setText(R.string.g_auth_success);

            itAccountName.setEnabled(false);
            btnChangeAccountPhoto.setEnabled(false);
            btnDeleteAccountPhoto.setEnabled(false);
            btnSignIn.setEnabled(false);
            btnSignOut.setEnabled(true);
        } else {
            itAccountName.setText(NotesSharedPreferences.getInstance().getUserAccountName());
            ivAccountPhoto.setImageBitmap(NotesSharedPreferences.getInstance().getBitmapPhoto());
            tvInfo.setText("...");

            itAccountName.setEnabled(true);
            btnChangeAccountPhoto.setEnabled(true);
            btnDeleteAccountPhoto.setEnabled(true);
            btnSignIn.setEnabled(true);
            btnSignOut.setEnabled(false);
        }
    }

    private void initDbSourceControls(View view) {
        rgDbSource = view.findViewById(R.id.radio_group_dbsource);
        rbSharedPref = view.findViewById(R.id.radio_button_shared_pref);
        rbFirebase = view.findViewById(R.id.radio_button_firebase);
        rbDataStore = view.findViewById(R.id.radio_button_datastore);

        if (NotesSharedPreferences.getInstance().getDBSource() == DATABASE.SHARED_PREF) {
            rbSharedPref.setChecked(true);
        }
        if (NotesSharedPreferences.getInstance().getDBSource() == DATABASE.FIREBASE) {
            rbFirebase.setChecked(true);
        }
        if (NotesSharedPreferences.getInstance().getDBSource() == DATABASE.DATASTORE) {
            rbDataStore.setChecked(true);
        }
        startDbSourceChecked = rgDbSource.getCheckedRadioButtonId();
    }

    private DATABASE getSelectedDbSource() {
        if (rbSharedPref.isChecked()) {
            return DATABASE.SHARED_PREF;
        }
        if (rbFirebase.isChecked()) {
            return DATABASE.FIREBASE;
        }
        if (rbDataStore.isChecked()) {
            return DATABASE.DATASTORE;
        }
        return null;
    }

    private void initDeletePhoto(View view) {
        btnDeleteAccountPhoto = view.findViewById(R.id.button_notes_delete_account_photo);
        btnDeleteAccountPhoto.setOnClickListener(v -> {
            Snackbar snackbar = Snackbar
                    .make(view, "Удалить фото профиля?", Snackbar.LENGTH_LONG)
                    .setAction(requireContext().getResources().getText(R.string.yes), new View.OnClickListener() {
                        @Override
                        public void onClick(View view1) {
                            setEmptyPhoto();
                        }
                    });
            snackbar.show();
        });
    }

    private void setEmptyPhoto() {
        NotesSharedPreferences.getInstance().saveUserPhotoToAssets(NotesSharedPreferences.getInstance().getEmptyPhoto());
        ivAccountPhoto.setImageBitmap(NotesSharedPreferences.getInstance().getEmptyPhoto());
        isPhotoChanged = true;
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
                    isPhotoChanged = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == RC_SIGN_IN) {
            // Когда сюда возвращается Task, результаты аутентификации уже готовы
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                accountGoogle = task.getResult(ApiException.class);
                updateUI();// Регистрация прошла успешно
                isPhotoChanged = true;
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void initButtonSaveSettings(View view) {
        btnSaveSettings = view.findViewById(R.id.button_notes_account_save);
        btnSaveSettings.setOnClickListener(v -> {
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
                if (isPhotoChanged == true) {
                    if (bitmapPhoto == null) {
                        NotesSharedPreferences.getInstance().saveUserPhotoToAssets(NotesSharedPreferences.getInstance().getEmptyPhoto());
                    } else {
                        NotesSharedPreferences.getInstance().saveUserPhotoToAssets(bitmapPhoto);
                    }
                    //progressBar.setVisibility(View.GONE);
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
            if (startDbSourceChecked != rgDbSource.getCheckedRadioButtonId()) {
                NotesData.getInstance().saveData();
                NotesSharedPreferences.getInstance().saveDBSource(getSelectedDbSource());
                result.append("Изменен источник данных: " + NotesSharedPreferences.getInstance().getDBSource().name() + "\n");
                NotesData.getInstance().loadData();
            }

            Snackbar.make(requireActivity().findViewById(R.id.image_view_notes_user_account_photo),
                    result.toString().equals("") ? "Нет изменений" : result.toString().replaceFirst("(.)[\n]$", "$1"), Snackbar.LENGTH_LONG).show();

            startDbSourceChecked = rgDbSource.getCheckedRadioButtonId();
            isPhotoChanged = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}