package com.financas.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

public class DrawableUtils {

    @SuppressLint("DiscouragedApi")
    public static int getIdDrawableByName(Context context, String drawableName) {
        Resources resources = context.getResources();
        return resources.getIdentifier(drawableName, "drawable", context.getPackageName());
    }
}
