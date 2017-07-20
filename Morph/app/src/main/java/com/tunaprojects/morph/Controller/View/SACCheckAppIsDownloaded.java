package com.tunaprojects.morph.Controller.View;

import android.content.Context;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Utility.Utils;
import com.tunaprojects.morph.Model.Session.Session;
import java.util.ArrayList;

/**
 * Created by Esteban Puello on 19/07/2017.
 * créé par fabrication pure
 */

public class SACCheckAppIsDownloaded {
    /**
     * This method updates the app whether it's online, if not it just open the downloaded data
     *
     * @param appname this parameter is the name of the app
     */
    public static void checkAppIsDownloaded(Context c, String appname) {
        if (Utils.isOnline(c)) {
            SACMakeAsyncCall.makeAsyncServerCall(c,"act=gavaa", appname, "updateappfiles1.dat", new ArrayList<>());
        } else {
            ArrayList<String> userdata = FileHandler.readFromFile(c, "userdata.dat");
            ArrayList<String> data = new ArrayList<>();
            createSessionData(appname, userdata, data);
            openSession(c, appname, data);
        }
    }

    private static void createSessionData(String appname, ArrayList<String> userdata, ArrayList<String> data) {
        for (String line :
                userdata) {
            String[] splited = line.split(";");
            if (splited[0].contentEquals(appname)) {
                data.add(new StringBuilder().append(splited[1]).append(";").append(splited[0]).append(".").append(splited[1]).append(";").append(splited[3]).toString());
            }
        }
    }

    private static void openSession(Context c, String appname, ArrayList<String> activities) {
        Session.getInstance().openSession(appname, activities, c);
    }
}
