package com.tunaprojects.morph.Controller.View;

import android.content.Context;
import android.view.View;

import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Utility.ExecuteLua;
import com.tunaprojects.morph.Controller.Utility.Utils;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.PropertyValue;
import com.tunaprojects.morph.Model.Instance.TObject;

import java.util.ArrayList;

/**
 * Created by Esteban Puello on 16/07/2017.
 */

public class Base2ActivityPropertyGetter {
    protected static String getElementProperty(Context c, final ArrayList<View> alviews, String id, String property) {
        ArrayList olddata = new ArrayList();
        ExecuteLua el = ExecuteLua.getInstance();
        olddata.add(id);
        olddata.add(property);
        el.updateGlobalInstances(c);
        el.addGlobal(new ArrayList(){{add(new TObject("alviews", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(alviews)));
        }}, new ArrayList<TObject>()));}});
        Object[] rt = el.executeLuaFunction(olddata, Utils.join(FileHandler.readFromFile(c, "propertygetter.dat"), " "));
        return rt[0].toString();
    }
}
