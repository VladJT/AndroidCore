package jt.projects.androidcore.notes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

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
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
            case R.id.action_exit:
                finish();
                return true;
        }
        return false;
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