package jt.projects.androidcore.calculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import jt.projects.androidcore.R;

public class ThemeDialog extends DialogFragment {

    private CalculatorActivity mainActivity;
    private boolean clickedOk = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mainActivity = (CalculatorActivity) getActivity();
        String[] singleChoiceItems = getResources().getStringArray(R.array.calc_themes);
        final int[] itemSelected = {0};

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainActivity);
        alertDialog.setTitle(getString(R.string.ChooseTheme));

        int currTheme = mainActivity.getCurrentAppThemeIndex();
        alertDialog.setSingleChoiceItems(singleChoiceItems, currTheme, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                itemSelected[0] = selectedIndex;
                clickedOk = true;
            }
        });
        alertDialog.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mainActivity.setAppTheme(((CalculatorActivity) getActivity()).themesMap.get(itemSelected[0]));
            }
        });
        alertDialog.setNegativeButton(getString(R.string.Cancel), null);
        return alertDialog.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (clickedOk) mainActivity.recreate();
    }
}
