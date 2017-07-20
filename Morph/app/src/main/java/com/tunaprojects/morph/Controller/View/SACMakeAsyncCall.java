package com.tunaprojects.morph.Controller.View;

import android.content.Context;

import com.tunaprojects.morph.Controller.AsyncCalls.AsyncUrlCall;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Utility.ExecuteLua;
import com.tunaprojects.morph.Controller.Utility.Utils;

import java.util.ArrayList;

/**
 * Created by Esteban Puello on 19/07/2017.
 * créé par fabrication pure
 */

public class SACMakeAsyncCall {
    /**
     * This function updates the globals and do a request to the server
     * @param act     what's going to be done
     * @param appname app's name
     */
    public static void makeAsyncServerCall(Context c, String act, String appname, String file, ArrayList al) {
        /*ArrayList al = new ArrayList() {{
            add(tv);
            add(ib);
            add(ll);
        }};*/
        ExecuteLua el = ExecuteLua.getInstance();
        el.updateGlobalInstances(c);
        AsyncUrlCall auc = new AsyncUrlCall(c, Utils.join(FileHandler.readFromFile(c, file), " "), al);
        auc.execute("https://tryxml.000webhostapp.com/morph-test/testing.php", act, "bring=" + appname);
    }
}
