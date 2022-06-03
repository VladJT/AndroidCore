package jt.projects.androidcore.notes;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import jt.projects.androidcore.R;

public class SharedPrefViewerDialogFragment extends BottomSheetDialogFragment {
    private OnDialogListener dialogListener;

    public static SharedPrefViewerDialogFragment newInstance() {
        return new SharedPrefViewerDialogFragment();
    }

    // Установим слушатель диалога
    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_shared_pref, container, false);
        setCancelable(false);// Запретим пользователю выходить без выбора
        view.findViewById(R.id.btnNo)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        if (dialogListener != null)
                            dialogListener.onDialogNo();
                    }
                });
        view.findViewById(R.id.btnYes)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        if (dialogListener != null)
                            dialogListener.onDialogYes();
                    }
                });

        TextView tvSharedPref = view.findViewById(R.id.text_view_shared_preferences);
        String result = NotesSharedPreferences.getAllPreferences();
        tvSharedPref.setText(result);
        return view;
    }
}