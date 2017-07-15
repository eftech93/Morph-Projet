package com.tunaprojects.morph.Controller.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tunaprojects.morph.Controller.AsyncCalls.AsyncUrlCall;
import com.tunaprojects.morph.Controller.Parser.Parser;
import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Esteban Puello on 29/11/2016.
 */

public class Utils {
    public Utils() {

    }

    public static String join(ArrayList<String> als, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String s :
                als) {
            sb.append(s);
            sb.append(separator);
        }
        return sb.toString();
    }

    public static ViewGroup.LayoutParams generateLayoutParams(int x, int y, ViewParent vp) {
        if (vp instanceof LinearLayout) {
            return new LinearLayout.LayoutParams(x, y);
        } else if (vp instanceof FrameLayout) {
            return new FrameLayout.LayoutParams(x, y);
        }
        return null;
    }

    public static boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static ArrayList<String> split(String str, String p) {
        ArrayList<String> ls = new ArrayList<>();
        Collections.addAll(ls, str.split(p));
        return ls;
    }

    public static void printJSON(JSONArray jArray) {
        try {
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                json_data.names();
                for (int j = 0; j < json_data.names().length(); j++) {
                    Log.i(json_data.names().get(j).toString(), json_data.getString(json_data.names().get(j).toString()));
                }
            }
        } catch (Exception e) {
            Log.e("AsyncUrlCall", "onPostExecute_error: " + e.getLocalizedMessage());
        }
    }

/*    public static void generteAsyncTask(String lua, ArrayList al, ArrayList params) {
        AsyncUrlCall atu = new AsyncUrlCall(lua, al);
        atu.execute(Parser.parserArrayListToArrayString(params));
    }
*/
    public static void generteAsyncTask(Context context, String lua, ArrayList al, ArrayList params) {
        AsyncUrlCall atu = new AsyncUrlCall(context, lua, al);
        atu.execute(Parser.parserArrayListToArrayString(params));
    }

    public static Bitmap getFinalImage(Bitmap bm, int max) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        if (width > max) {
            float rate = (float) width / height;
            width = max;
            Float d = width / rate;
            height = d.intValue();
        } else if (height > max) {
            float rate = (float) height / width;
            height = max;
            Float d = height / rate;
            width = d.intValue();
        }
        return Bitmap.createScaledBitmap(bm, width,
                height, false);
    }

    /*il faut changé la methode enlever le context c et mettre a jour le fichier lua propertyparse.dat dans onClick et onCheckedChanged*/
    public static Object[] executeLuaFunction(ArrayList args, String lua) {
        ExecuteLua el = ExecuteLua.getInstance();
        return el.executeLuaFunction(args, lua);

    }

    public static void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public static void openViewInDialog(final Context x , View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperDialog.openChildViewAsDialog(x, view, this);
            }
        });
    }

}
