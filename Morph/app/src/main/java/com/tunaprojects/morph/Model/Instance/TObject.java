package com.tunaprojects.morph.Model.Instance;

/**
 * Created by Esteban Puello on 4/08/2016.
 */

import java.util.ArrayList;


public class TObject {

    private String tclass;
    private java.util.ArrayList<Property> properties;
    private ArrayList<TObject> childs;

    public TObject() {
    }

    public TObject(String tclass, java.util.ArrayList<Property> proper, ArrayList<TObject> childs) {
        this.properties = proper;
        this.tclass = tclass;
        this.childs = childs;
    }

    public void setTclass(String tclass) {
        this.tclass = tclass;
    }

    public void setChilds(ArrayList<TObject> childs) {
        this.childs = childs;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

    public ArrayList<TObject> getChilds() {
        return childs;
    }

    public ArrayList<Property> getProperties() {
        return this.properties;
    }

    public String getTclass() {
        return this.tclass;
    }

    public Property searchPropertyByName(String name) {
        for (Property p : properties) {
            if (p.getPropertyName().contains(name)) {
                return p;
            }
        }
        return null;
    }
}
