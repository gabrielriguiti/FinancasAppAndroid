package com.financas.utils;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.financas.R;

public class MessageUtils {

    public static void showAlert(Context context, String msg) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Alerta")
                .setMessage(msg)
                .setPositiveButton("Fechar", (dialogInterface, i) -> {
                })
                .show();
    }

    public static void showInfo(Context context, String msg) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Informação")
                .setMessage(msg)
                .setPositiveButton("Fechar", (dialogInterface, i) -> {
                })
                .show();
    }
}
