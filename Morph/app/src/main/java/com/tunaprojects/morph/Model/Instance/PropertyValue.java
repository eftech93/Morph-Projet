package com.tunaprojects.morph.Model.Instance;

/**
 * Created by Esteban Puello on 4/08/2016.
 */

public class PropertyValue extends Object implements Cloneable {

    private final Object obj;

    public PropertyValue(Object obj) {
        this.obj = obj;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getValue(){
        return this.obj;
    }

}
