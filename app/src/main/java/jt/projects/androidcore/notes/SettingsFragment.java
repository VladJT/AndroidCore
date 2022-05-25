package jt.projects.androidcore.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
