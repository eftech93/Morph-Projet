package com.tunaprojects.morph.Controller.View;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Utility.Utils;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.TObject;
import com.tunaprojects.morph.Model.Session.Session;
import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import com.tunaprojects.morph.View.AbstractActivity;
import com.tunaprojects.morph.View.Bases.Base2Activity;

import java.util.ArrayList;

/**
 * Created by Esteban Puello on 5/07/2017.
 * Patron controlleur
 */

public class Base2ActivityController {
    private final AbstractActivity activity;
    private final ArrayList oldData = new ArrayList();
    private Property filename;
    private ArrayList<View> alviews;
    private B2ACLoadFullPage b2aclfp;
    private B2ACLoadContent b2aclc;

    public Base2ActivityController(AbstractActivity c) {
        this.activity = c;
        ArrayList elements = FileHandler.readFromFile(this.activity, "elements.dat");
        ArrayList containers = FileHandler.readFromFile(this.activity, "containers.dat");
        ArrayList views = FileHandler.readFromFile(this.activity, "view.dat");
        this.alviews = new ArrayList<>();
        this.b2aclfp = new B2ACLoadFullPage(this.activity, views, elements, containers);
        this.b2aclc = new B2ACLoadContent(this.activity, views, elements, containers);
    }

    public String getElementValue(String id, String property) {
        return B2ACPropertyGetter.getElementProperty(this.activity, this.alviews, id, property);
    }

    /**
     * this method is used to load the information of the file, it calls loadFullPage method
     *
     * @param filename file name to be loadded
     */
    private void loadPage(Property filename) {
        ArrayList<String> als = FileHandler.readFromFile(this.activity, filename.getPropertyValue().toString());
        final String xml = Utils.join(als, "");
        this.alviews = this.b2aclfp.loadFullPage(xml, oldData);
    }

    /**
     * This method is called when a page different from main will be loadded
     *
     * @param activityName name of the activity to be loadded
     */
    private void startWithPage(String activityName) {
        if (!activityName.isEmpty()) {
            Session s = Session.getInstance();
            TObject app = s.getElement("app");
            if (app != null) {
                boolean flag = searchByName("name", activityName, app);
                if (!flag) {
                    this.activity.finish("File not found");
                }
            } else {
                this.activity.finish("No app loaded");
            }
        } else {
            this.activity.finish("Activity name empty");
        }
    }

    /**
     * this method search the filename and the load the page calling loadPage method
     *
     * @param property this is a string containing the name of the property to be searched
     * @param name     this is the value that the property should have
     * @param tObject  this' the the list of object in which the property will be searched
     * @return returns a flag whether or not it was found
     */
    private boolean searchByName(String property, String name, TObject tObject) {
        boolean flag = false;
        for (TObject child : tObject.getChilds()) {
            Property val = child.searchPropertyByName(property);
            if (val != null && child != null && val.getPropertyValue().toString().contentEquals(name)) {
                Property filename = child.searchPropertyByName("filename");
                if (filename != null) {
                    loadPage(filename);
                    this.filename = filename;
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    private void startWithMain() {
        Session s = Session.getInstance();
        TObject app = s.getElement("app");
        if (app != null) {
            boolean flag = searchByName("type", "main", app);
            if (!flag) {
                this.activity.finish("Main file not found");
            }
        } else {
            this.activity.finish("No app loaded");
        }
    }

    public void start(Intent i) {
        try {
            String activityName = i.getStringExtra("activity");
            if (activityName != null) {
                startWithPage(activityName);
            } else {
                startWithMain();
            }
        } catch (Exception e) {
            for (StackTraceElement ste :
                    e.getStackTrace()) {
                Log.e("Error ", ste.toString());
            }
        }
    }

    public void newPage(String pageName) {
        Intent i = new Intent(this.activity, Base2Activity.class);
        i.putExtra("activity", pageName);
        this.activity.startActivity(i);
    }

    public void onRestart() {
        loadPage(this.filename);
    }

    public void loadContent(String pageName) {
        this.alviews = this.b2aclc.loadContent(pageName, this.b2aclfp.getLoadType(), this.oldData);
    }

}
