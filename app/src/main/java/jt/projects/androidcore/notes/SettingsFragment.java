package jt.projects.androidcore.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import jt.projects.androidcore.R;
import jt.projects.androidcore.cities.EmblemFragment;

public class SettingsFragment extends Fragment {

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
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
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
    }
}
