package com.tunaprojects.morph.Model.Instance;

/**
 * Created by Esteban Puello on 4/08/2016.
 */
public class Property {
    private String name;
    private PropertyValue value;
    private final java.util.ArrayList<PropertyValue> oldValues;

    public Property(String name, PropertyValue value) {
        this.name = name;
        this.value = value;
        this.oldValues = new java.util.ArrayList<>();
    }

    public Property() {
        oldValues = new java.util.ArrayList<>();
    }

    public String getPropertyName(){
        return this.name;
    }
    public Object getPropertyValue(){
        return this.value.getValue();
    }
    public void setPropertyValue(Object newValue){
        try {
            this.oldValues.add((PropertyValue)this.value.clone());
            this.value = new PropertyValue(newValue);
        } catch (CloneNotSupportedException ex) {
            // Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public java.util.ArrayList getAllOldsValues(){
        return this.oldValues;
    }
}
