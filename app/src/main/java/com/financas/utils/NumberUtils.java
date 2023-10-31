package com.financas.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

    public static String formatarDuasCasasVirgula(double n) {
        return new DecimalFormat("#,##0.00").format(n);
    }

    public static String formatRealBrasileiro(double n){
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(n);
    }
}
