package com.example.bank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class DialogFragmentClass extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.login, null));

        //Setting message manually and performing action on button click
        builder.setCancelable(false)
                .setNegativeButton("Отмена", (dialog, id) -> dialog.cancel())
                .setPositiveButton("Добавить", (dialog, id) -> {
                    dialog.cancel();
                    Toast.makeText(getActivity(), "you choose no action for alertbox",
                            Toast.LENGTH_SHORT).show();
                })
                .setTitle("Авторизация");
        return builder.create();
    }
}
