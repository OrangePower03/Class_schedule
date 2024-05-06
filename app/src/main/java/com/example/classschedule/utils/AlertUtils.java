package com.example.classschedule.utils;

import android.content.Context;
import android.widget.Toast;

public class AlertUtils {
    public static void showAlert(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
