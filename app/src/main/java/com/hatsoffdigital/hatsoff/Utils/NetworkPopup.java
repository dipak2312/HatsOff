package com.hatsoffdigital.hatsoff.Utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hatsoffdigital.hatsoff.R;

public class NetworkPopup {

    public static void ShowPopup(final Context context)
    {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.check_internate_connection, null);
        dialogBuilder.setView(dialogView);

        RelativeLayout rel_ok=(RelativeLayout)dialogView.findViewById(R.id.rel_ok);

        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();

        rel_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.dismiss();
            }
        });


    }
}
