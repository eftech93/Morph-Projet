package com.tunaprojects.morph.Controller.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteStatement;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Esteban Puello on 4/08/2016.
 */
public class Dialog {
    private static AlertDialog.Builder ab;
    public static void showYesNoAlert(Context context, String message){
        ab = new AlertDialog.Builder(context);
        ab.setMessage(message);
        ab.setCancelable(true);
        ab.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        ab.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = ab.create();
        alert11.show();
    }
    public static void showAlert(Context context, String message){
        ab = new AlertDialog.Builder(context);
        ab.setMessage(message);
        ab.setCancelable(true);
        ab.setPositiveButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = ab.create();
        alert11.show();
    }

    public static void showList(Context context, final String[] list, String title, View v){
        try {
            final TextView tv = ((TextView) v);
            ab = new AlertDialog.Builder(context);
            ab.setTitle(title);
            final AlertDialog alert;
            ab.setItems(list, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    tv.setText(list[item]);
                    dialog.cancel();
                }
            });
            alert = ab.create();
            alert.show();
        }catch(Exception e){
            Dialog.showAlert(context, "Error: " + e.getMessage());
        }
    }
    public static void showListSql(Context context, SQLiteStatement sqLiteStatement,  View v){
        final CharSequence[] items = {"A", "B", "C","D", "E","F","G","H","I","J", "K","L","M"};
        final TextView tv = ((TextView) v);
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setTitle("Make your selection");
        final AlertDialog alert = ab.create();
        alert.show();
        ab.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                tv.setText(items[item]);
                dialog.cancel();
                //mDoneButton.setText(items[item]);
            }
        });

    }
    public static void showDateChooser(Context context,final View view, String title){
        DatePicker dp = new DatePicker(context);
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setTitle(title);
        ab.setView(dp);
        final AlertDialog alert = ab.create();
        alert.show();
        Calendar gc = new GregorianCalendar();
        dp.init(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                ((TextView) view).setText(i2 + "/" + i1 + "/" + i);
                alert.cancel();
            }
        });
    }
    public static void showToastDialog(Context context, String message,int longitud){
        if (longitud == Toast.LENGTH_LONG || longitud == Toast.LENGTH_SHORT){
            Toast.makeText(context, message, longitud).show();
        }else{
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
    public static void showTextDialog(Context context, final TextView tv, String type){
        try {
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ab = new AlertDialog.Builder(context);
            ab.setView(ll);
            final EditText et = new EditText(context);
            if (type.equals("number")) {
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (type.equals("email")) {
                et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            } else if (type.equals("phone")) {
                et.setInputType(InputType.TYPE_CLASS_PHONE);
            } else {
                et.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            ll.addView(et);
            Button b = new Button(context);
            ll.addView(b);
            b.setText("Aceptar");
            final AlertDialog alert = ab.create();
            alert.show();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv.setText(et.getText());
                    alert.cancel();
                }
            });
        }catch(Exception e){
            Dialog.showAlert(context, "Error: " + e.getMessage() + " Dialog.ShowTextDialog");
        }
    }
    public static void showViewAsDialog(Context context, View v){
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ab = new AlertDialog.Builder(context);
        ab.setView(ll);
        Button b = new Button(context);
        ll.addView(v);
        ll.addView(b);
        final AlertDialog alert;
        alert = ab.create();
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();
    }
    public static void showViewAsDialog(Context context, List<View> lv){
        ScrollView sv = new ScrollView(context);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ab = new AlertDialog.Builder(context);
        ab.setView(sv);
        sv.addView(ll);
        for (View v :
                lv) {
            ll.addView(v);
        }
        final AlertDialog alert = ab.create();
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();
    }

    public static void showViewAsDialog(Context context, View v, Button.OnClickListener on ){
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ab = new AlertDialog.Builder(context);
        ab.setView(ll);
        Button b = new Button(context);
        ll.addView(v);
        ll.addView(b);
        b.setText("Aceptar");
        final AlertDialog alert = ab.create();
        alert.show();
        b.setOnClickListener(on);
    }
    public static void showViewAsDialog(Context context, List<View> lv, Button.OnClickListener on){
        ScrollView sv = new ScrollView(context);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ab = new AlertDialog.Builder(context);
        ab.setView(sv);
        sv.addView(ll);
        Button b = new Button(context);
        for (View v :
                lv) {
            ll.addView(v);
        }
        ll.addView(b);
        b.setText("Aceptar");
        final AlertDialog alert = ab.create();
        alert.show();
        b.setOnClickListener(on);
    }
    public static void activityAsADialog(Context context, Activity activity){
        ab = new AlertDialog.Builder(context);
    }
}