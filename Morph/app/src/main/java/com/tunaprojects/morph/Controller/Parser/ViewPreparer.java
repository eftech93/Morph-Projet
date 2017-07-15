package com.tunaprojects.morph.Controller.Parser;

import android.content.Context;
import android.view.View;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.PropertyValue;
import com.tunaprojects.morph.Model.Instance.TObject;
import com.tunaprojects.morph.Controller.Utility.ExecuteLua;
import com.tunaprojects.morph.Controller.Utility.Utils;
import java.util.ArrayList;

/**
 * Created by Esteban Puello on 26/11/2016.
 */
public class ViewPreparer {

    public ViewPreparer(){

    }
    private static ViewPreparer instance_ = null;
    private ExecuteLua el;
    public static ViewPreparer getInstance() {
        if (instance_ == null) {
            instance_ = new ViewPreparer();
            instance_.el = ExecuteLua.getInstance();
        }
        return instance_;
    }

    private void setPreperties(Context c, final View v, final ArrayList<Property> alp, ArrayList oldData) {
        instance_.el.updateGlobalInstances(c);
        ArrayList objs = new ArrayList();
        objs.add(new TObject("this_", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(v)));
        }}, new ArrayList<TObject>()));
        objs.add(new TObject("alp", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(alp)));
        }}, new ArrayList<TObject>()));
        instance_.el.addGlobal(objs);
        instance_.el.executeLuaFunction(oldData, Utils.join(FileHandler.readFromFile(c, "propertyparser.dat"), " "));
    }

    public View generateBaseElement(final Context c, final TObject vc, final ArrayList oldData) {
        ArrayList objs_ = new ArrayList();
        objs_.add(vc);
        objs_.add(oldData);
        instance_.el.updateGlobalInstances(c);
        Object[] rt = instance_.el.executeLuaFunction(objs_, Utils.join(FileHandler.readFromFile(c, "viewparser.dat"), " "));
        final View v = (View) rt[0];
        v.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                setPreperties(c, v, vc.getProperties(), oldData);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });
        return v;
    }
}
