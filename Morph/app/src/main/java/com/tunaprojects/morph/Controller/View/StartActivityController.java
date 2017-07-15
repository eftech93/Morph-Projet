package com.tunaprojects.morph.Controller.View;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tunaprojects.morph.Controller.AsyncCalls.AsyncUrlCall;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Parser.Parser;
import com.tunaprojects.morph.Controller.Utility.ExecuteLua;
import com.tunaprojects.morph.Controller.Utility.Utils;
import com.tunaprojects.morph.Model.Session.Session;
import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import com.tunaprojects.morph.R;
import com.tunaprojects.morph.View.StartActivity;

import java.util.ArrayList;

/**
 * Created by Esteban Puello on 7/07/2017.
 */

public class StartActivityController {
    private static final String FIRST_LOAD_SYSTEM_FILE = "function m_run(olddata) " +
            "   jsonarray = parser:stringToJSONArray(olddata:get(0)) " +
            "   als = luajava.newInstance('java.util.ArrayList') " +
            "   for i = 0, jsonarray:length() - 1, 1 do " +
            "       jsondata = jsonarray:getJSONObject(i) " +
            "       als:add(jsondata:getString('name') .. ';' .. jsondata:getString('version')) " +
            "       file = luajava.newInstance('java.util.ArrayList') " +
            "       str = jsondata:getString('file') " +
            "       file:add(str) " +
            "       fh:saveFile(context, jsondata:getString('name'), parser:parserArrayListObjectToArrayListString(file)) " +
            "   end " +
            "   fh:saveFile(context, 'systemfiles.dat', parser:parserArrayListObjectToArrayListString(als)) " +
            "   olddata:get(2):setVisibility(4)" +
            "   olddata:get(3):setVisibility(0)" +
            "end ";
    private static final String SYSTEM_FILES = "systemfiles.dat";
    private ArrayList al;
    private final TextView tv;
    private final ImageButton ib;
    private final ImageButton ib1;
    private final AutoCompleteTextView et;
    private final LinearLayout ll;
    private final StartActivity sa;
    private ExecuteLua el;

    public StartActivityController(final StartActivity sa) {
        this.sa = sa;
        this.tv = (TextView) sa.findViewById(R.id.tv);
        this.ib = (ImageButton) sa.findViewById(R.id.ib);
        this.ib1 = (ImageButton) sa.findViewById(R.id.ibsa);
        this.et = (AutoCompleteTextView) sa.findViewById(R.id.et);
        this.et.setAdapter(getAdapter());
        this.ll = (LinearLayout) sa.findViewById(R.id.llsa);
        this.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll.setVisibility(View.INVISIBLE);
                ib.setVisibility(View.INVISIBLE);
                checkLocalData();
            }
        });
        this.ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et.getText().toString();
                if (name.length() > 20) {
                    SuperDialog.createToastMessage(sa, sa.getString(R.string.error_twenty_length));
                    et.setText("");
                } else if (name.isEmpty()) {
                    SuperDialog.createToastMessage(sa, sa.getString(R.string.error_empty_length));
                    et.setText("");
                } else {
                    checkApp(et.getText().toString());
                }
            }
        });
        this.el = ExecuteLua.getInstance();
    }

    private void checkApp(String appname) {
        ArrayList<String> als = FileHandler.readFromFile(this.sa, "userapps.dat");
        if (als.contains(appname)) {
            checkAppIsDownloaded(appname);
        } else {
            checkAppIsNotDownloaded(appname);
        }

    }

    /**
     * This method updates the app whether it's online, if not it just open the downloaded data
     *
     * @param appname this parameter is the name of the app
     */
    private void checkAppIsDownloaded(String appname) {
        if (Utils.isOnline(this.sa)) {
            makeAsyncServerCall("act=gavaa", appname, "updateappfiles1.dat");
        } else {
            ArrayList<String> userdata = FileHandler.readFromFile(sa, "userdata.dat");
            ArrayList<String> data = new ArrayList<>();
            for (String line :
                    userdata) {
                String[] splited = line.split(";");
                if (splited[0].contentEquals(appname)) {
                    data.add(new StringBuilder().append(splited[1]).append(";").append(splited[0]).append(".").append(splited[1]).append(";").append(splited[3]).toString());
                }
            }
            openSession(appname, data);
        }
    }

    /**
     * This function updates the globals and do a request to the server
     *
     * @param act     what's going to be done
     * @param appname app's name
     */
    private void makeAsyncServerCall(String act, String appname, String file) {
        al = new ArrayList() {{
            add(tv);
            add(ib);
            add(ll);
        }};
        el.updateGlobalInstances(this.sa);
        AsyncUrlCall auc = new AsyncUrlCall(this.sa, Utils.join(FileHandler.readFromFile(this.sa, file), " "), al);
        auc.execute("https://tryxml.000webhostapp.com/morph-test/testing.php", act, "bring=" + appname);
    }

    /**
     * this method tries to download the data of the app whether it's online, if not, it shows a message
     *
     * @param appname this is the app's name
     */
    private void checkAppIsNotDownloaded(String appname) {
        //if doesn´t have it, download all data
        if (Utils.isOnline(this.sa)) {
            makeAsyncServerCall("act=gaaop", appname, "getfirstuserapp.dat");
        } else {
            //if does not have it, and there's no internet access, show show a message and the refresh button
            SuperDialog.createToastMessage(this.sa, "App not downloaded please connect to internet");
        }
    }

    /**
     * This method opens a session for the app
     *
     * @param appname    the app's name
     * @param activities the activities' name
     */
    public void openSession(String appname, ArrayList<String> activities) {
        Session.getInstance().openSession(appname, activities, this.sa);
    }

    public void start() {
        checkLocalData();
    }

    protected void checkLocalData() {
        ArrayList<String> systemsilesal = FileHandler.readFromFile(this.sa, SYSTEM_FILES);
        if (Utils.isOnline(sa)) {
            checkLocalDataIsOnline(systemsilesal);
        } else {
            checkLocalDataInNotOnline(systemsilesal);
        }
    }

    private void checkLocalDataInNotOnline(ArrayList<String> systemsilesal) {
        if (systemsilesal.isEmpty()) {

            SuperDialog.openDialog(sa, sa.getString(R.string.alert), sa.getString(R.string.no_internet_access));
            this.ib.setVisibility(View.VISIBLE);
            this.ll.setVisibility(View.INVISIBLE);
        } else {
            SuperDialog.createToastMessage(sa, sa.getString(R.string.data_no_updated));
            this.ib.setVisibility(View.VISIBLE);
            this.ll.setVisibility(View.VISIBLE);
        }
    }

    private void checkLocalDataIsOnline(ArrayList<String> systemsilesal) {
        al = new ArrayList() {{
            add(tv);
            add(ib);
            add(ll);
        }};
        el.updateGlobalInstances(sa);
        if (systemsilesal.isEmpty()) {
            //first time
            AsyncUrlCall auc = new AsyncUrlCall(sa, FIRST_LOAD_SYSTEM_FILE, al);
            auc.execute("https://tryxml.000webhostapp.com/morph-test/testing.php", "act=gasf");
        } else {
            //check whether or not it needs to update
            String updatefiles1 = Utils.join(FileHandler.readFromFile(sa, "updatesystemfiles1.dat"), " ");
            AsyncUrlCall auc = new AsyncUrlCall(sa, updatefiles1, al);
            auc.execute("https://tryxml.000webhostapp.com/morph-test/testing.php", "act=gasfv");
        }
    }

    private ArrayAdapter<String> getAdapter() {
        ArrayList<String> als = FileHandler.readFromFile(sa, "userapps.dat");
        return Parser.getArrayAdapter(sa, als);
    }

    public void onResume() {
        Session s = Session.getInstance();
        s.empty();
    }
}
