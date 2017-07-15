package com.tunaprojects.morph.Model.Session;

import android.content.Context;
import android.content.Intent;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.PropertyValue;
import com.tunaprojects.morph.Model.Instance.TObject;
import com.tunaprojects.morph.View.Bases.Base2Activity;
import java.util.ArrayList;

/**
 * Created by Esteban Puello on 3/12/2016.
 */

public class Session {
    private static Session instance = null;
    private ArrayList<TObject> elements = null;

    private Session() {
        this.elements = new ArrayList<>();
    }

    public void addElement(String tclass, ArrayList<Property> properties, ArrayList<TObject> childs) {
        this.elements.add(new TObject(tclass, properties, childs));
    }

    public void empty() {
        elements.clear();
    }

    public TObject getElement(String tclass) {
        for (TObject t :
                this.elements) {
            if (t.getTclass().contentEquals(tclass)) {
                return t;
            }
        }
        return null;
    }

    public void openSession(final String appname, final ArrayList<String> activities, Context x) {
        Session s = Session.getInstance();
        s.empty();
        ArrayList<TObject> to = new ArrayList<>();
        for (String activity :
                activities) {
            final String[] splited = activity.split(";");
            to.add(new TObject("activity", new ArrayList<Property>() {{
                add(new Property("name", new PropertyValue(splited[0])));
                add(new Property("filename", new PropertyValue(splited[1])));
                add(new Property("type", new PropertyValue(splited[2])));
            }}, new ArrayList<TObject>()));
        }
        s.addElement(new TObject("app", new ArrayList<Property>() {{
            add(new Property("name", new PropertyValue(appname)));
        }}, to));
        Intent intent = new Intent(x, Base2Activity.class);
        x.startActivity(intent);
    }

    public void addObject(TObject tObject) {
        Session s = Session.getInstance();
        s.addElement(tObject);
    }

    public void addElement(TObject tObject) {
        this.elements.add(tObject);
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
}
