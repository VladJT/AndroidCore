package jt.projects.androidcore.notes;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.NotSerializableException;

import jt.projects.androidcore.R;
import jt.projects.androidcore.common.ConfigInfo;

public class NotesMainActivity extends NotesBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initNavigationDrawer(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View hView = navigationView.getHeaderView(0);
        ImageView i = hView.findViewById(R.id.image_view_notes_user_account);
        i.setImageBitmap(NotesSharedPreferences.getBitmapPhoto());

        // Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        // пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_list_fragment_container, new NotesListFragment())
                    .commit();
        }
    }

    // инициализируем Navigation Drawer
    private void initNavigationDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

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
                twUserAccountName.setText(NotesSharedPreferences.getUserAccountName());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                ImageView i = drawerView.findViewById(R.id.image_view_notes_user_account);
                i.setImageBitmap(NotesSharedPreferences.getBitmapPhoto());
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
        }
        return false;
    }

    private void changeTheme() {
        String[] items = getResources().getStringArray(R.array.choose_notes_theme);
        // Создаём билдер и передаём контекст приложения
        AlertDialog.Builder ab = new AlertDialog.Builder(NotesMainActivity.this);

        // В билдере указываем заголовок окна. Можно указывать как ресурс, так и строку
        ab.setTitle("Выбор темы");
        ab.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int currentTheme = R.style.Theme_NotesTheme;
                switch (which) {
                    case 0:
                        currentTheme = R.style.Theme_NotesTheme;
                        break;
                    case 1:
                        currentTheme = R.style.Theme_NotesDarkTheme;
                        break;
                    case 2:
                        return;
                }
                NotesSharedPreferences.saveAppTheme(currentTheme);
                recreate();
            }
        });
        ab.create().show();
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (ConfigInfo.isLandscape(getApplicationContext())) {
            ft.replace(R.id.notes_info_fragment_container, fragment);
        } else {
            ft.replace(R.id.notes_list_fragment_container, fragment);
        }

        boolean needAddToStack = false;
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f instanceof NotesListFragment & f.isVisible()) {
                needAddToStack = true;
            }
        }
        if (needAddToStack) {
            ft.addToBackStack("");
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}