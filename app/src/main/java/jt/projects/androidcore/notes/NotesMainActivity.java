package jt.projects.androidcore.notes;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.NotSerializableException;

import jt.projects.androidcore.R;
import jt.projects.androidcore.calculator.BaseActivity;
import jt.projects.androidcore.common.ConfigInfo;
import jt.projects.androidcore.notes.data.Note;

public class NotesMainActivity extends NotesBaseActivity {
    Switch switchTheme;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }

        initNavigationDrawer(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View hView = navigationView.getHeaderView(0);
        ImageView i = hView.findViewById(R.id.image_view_notes_user_account);
        i.setImageBitmap(NotesSharedPreferences.getInstance().getBitmapPhoto());

        // Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        // пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_list_fragment_container, new NotesListFragment())
                    .commit();
        }

        initSwitchTheme();
    }

    private void initSwitchTheme() {
        switchTheme = findViewById(R.id.switch_theme);
        if (NotesSharedPreferences.getInstance().getAppTheme() == R.style.Theme_NotesDarkTheme) {
            switchTheme.setChecked(true);
        }

        switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchTheme.isChecked()) {
                    NotesSharedPreferences.getInstance().saveAppTheme(R.style.Theme_NotesDarkTheme);
                    recreate();
                } else {
                    NotesSharedPreferences.getInstance().saveAppTheme(R.style.Theme_NotesTheme);
                    recreate();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // мы получаем Меню приложения и с помощью специального MenuInflater’а (по
        //аналогии с LayoutInflater’ом) надуваем кнопки в получаемом меню.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        checkMenuItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    // инициализируем Navigation Drawer
    private void initNavigationDrawer(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);

        // Создаем ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, // для людей с плохим зрением - проговаривается голосом
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                TextView twUserAccountName = drawerView.findViewById(R.id.text_view_notes_user_account);
                twUserAccountName.setText(NotesSharedPreferences.getInstance().getUserAccountName());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                ImageView i = drawerView.findViewById(R.id.image_view_notes_user_account);
                i.setImageBitmap(NotesSharedPreferences.getInstance().getBitmapPhoto());
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item ->
        {
            drawer.close();
            return checkMenuItemSelected(item.getItemId());
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!(getSupportFragmentManager().getFragments().get(0) instanceof NotesListFragment)) {
            getSupportFragmentManager().popBackStack();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Выход")
                .setMessage("Вы уверены, что хотите выйти?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private boolean checkMenuItemSelected(int id) {
        switch (id) {
            case R.id.action_back:
                getSupportFragmentManager().popBackStack();
                return true;
            case R.id.action_settings:
                showFragment(new SettingsFragment());
                return true;
            case R.id.action_about:
                showFragment(new AboutFragment());
                return true;
            case R.id.action_theme:
                changeTheme();
                return true;
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_sh_pref:
                showSharedPreferencesDialog();
                return true;
        }
        return false;
    }

    private void showSharedPreferencesDialog() {
        OnDialogListener dialogListener = new OnDialogListener() {
            @Override
            public void onDialogNo() {
                Toast.makeText(getApplicationContext(), "Нажата No", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDialogYes() {
                Toast.makeText(getApplicationContext(), "Нажата Yes", Toast.LENGTH_SHORT).show();
            }
        };
        SharedPrefViewerDialogFragment dialogFragment = SharedPrefViewerDialogFragment.newInstance();
        dialogFragment.setOnDialogListener(dialogListener);
        dialogFragment.show(getSupportFragmentManager(), "dialog_fragment");
    }

    private void changeTheme() {
        String[] items = getResources().getStringArray(R.array.choose_notes_theme);
        // Создаём билдер и передаём контекст приложения
        AlertDialog.Builder ab = new AlertDialog.Builder(NotesMainActivity.this)
                .setTitle("Выбор темы")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int currentTheme = R.style.Theme_NotesTheme;
                        switch (which) {
                            case 0:
                                currentTheme = R.style.Theme_NotesTheme;
                                switchTheme.setChecked(false);
                                break;
                            case 1:
                                currentTheme = R.style.Theme_NotesDarkTheme;
                                switchTheme.setChecked(true);
                                break;
                            case 2:
                                return;
                        }
                        NotesSharedPreferences.getInstance().saveAppTheme(currentTheme);
                        recreate();
                    }
                });
        ab.create().show();
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.notes_list_fragment_container, fragment);
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f instanceof NotesListFragment & f.isVisible()) {
                ft.addToBackStack("");
                break;
            }
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}