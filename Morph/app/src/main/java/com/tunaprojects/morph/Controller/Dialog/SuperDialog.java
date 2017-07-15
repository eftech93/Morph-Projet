package com.tunaprojects.morph.Controller.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Esteban Puello on 25/10/2016.
 */

public class SuperDialog {

    public SuperDialog() {
    }

    /**
     * This function opens a simple dialog
     *
     * @param x       the context in which the dialog will be displayed
     * @param title   the title to be shown
     * @param message the message to be shown
     */
    public static void openDialog(Context x, String title, String message) {
            final AlertDialog alertDialog = new AlertDialog.Builder(x).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.show();
    }

    /**
     * create a toast message
     *
     * @param x
     * @param message
     */

    public static void createToastMessage(Context x, String message) {
        createToastMessage(x, message, Toast.LENGTH_SHORT);
    }

    public static void createLogMessage(String tag, String message) {
        Log.i(tag, message);
    }

    public static void createLogMessage(String tag, StringBuilder message) {
        Log.i(tag, message.toString());
    }

    public static void createToastMessage(Context x, String message, int dur) {
        if (dur < 0 || dur > 1) {
            dur = 0;
        }
        //buscar como hacer esto y como resolverlo....
        Toast toast = Toast.makeText(x, message, dur == 0 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        toast.show();
    }

    public static AlertDialog getOpenedDialog(Context x, String title, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(x).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog openChildViewAsDialog(final Context x, final View view, final View.OnClickListener vocl) {
        try {
            final ViewGroup parent = (ViewGroup) view.getParent();
            final int pos = parent.indexOfChild(view);
            final ViewGroup.LayoutParams vglp = view.getLayoutParams();
            parent.removeView(view);
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(x);
            alertDialog.setView(view);
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    ((ViewGroup) view.getParent()).removeView(view);
                    parent.addView(view, pos, vglp);
                    view.setOnClickListener(vocl);
                }
            });
            final AlertDialog ad = alertDialog.show();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ad.cancel();
                }
            });
            return ad;
        } catch (Exception e) {
            return openViewAsDialog(x, view, vocl);
        }
    }

    public static AlertDialog openViewAsDialog(final Context x, final View view, final View.OnClickListener vocl) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(x);
        alertDialog.setView(view);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                view.setOnClickListener(vocl);
            }
        });
        final AlertDialog ad = alertDialog.show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.cancel();
            }
        });
        return ad;
    }

    public static AlertDialog openViewAsDialog(final Context x, final View view) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(x);
        alertDialog.setView(view);
        final AlertDialog ad = alertDialog.show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.cancel();
            }
        });
        return ad;
    }

    public static void openDialogCustomYesNo(Context x, String title, View view, DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no) {
        new AlertDialog.Builder(x)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(android.R.string.yes, yes)
                .setNegativeButton(android.R.string.no, no)
                .show();
    }

    public static AlertDialog openDialog(Context x, String title, View view) {
        return new AlertDialog.Builder(x)
                .setTitle(title)
                .setView(view)
                .show();
    }


    public static AlertDialog.Builder openNoCancelableDialog(Context x, String title, View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(x);
        alertDialog.setCancelable(false);
        alertDialog.setView(view);
        alertDialog.setTitle(title);
        alertDialog.show();
        return alertDialog;
    }

}
