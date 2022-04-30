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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] singleChoiceItems = getResources().getStringArray(R.array.calc_themes);
        final int[] itemSelected = {0};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Выберите тему");

        alertDialog.setSingleChoiceItems(singleChoiceItems, itemSelected[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                itemSelected[0] = selectedIndex;
            }
        });
        alertDialog.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((CalculatorActivity) getActivity()).setAppTheme(((CalculatorActivity) getActivity()).themesMap.get(itemSelected[0]));
                //     getActivity().recreate();
            }
        });
        alertDialog.setNegativeButton(getString(R.string.Cancel), null);
        return alertDialog.create();
    }


}
