package com.tunaprojects.morph.Controller.Utility;

import android.content.Context;
import android.util.Log;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Parser.Parser;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.PropertyValue;
import com.tunaprojects.morph.Model.Instance.TObject;
import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;
import java.util.ArrayList;

/**
 * Created by Esteban Puello on 27/11/2016.
 */

public class ExecuteLua {
    private final ArrayList globals = new ArrayList();
    private final ArrayList<LuaState> alL = new ArrayList<>();
    private static ExecuteLua el;

    public static ExecuteLua getInstance(){
        if(el == null){
            el = new ExecuteLua();
        }
        return el;
    }

    private ExecuteLua() {
    }

    public void closeAllState() {
        for (LuaState l :
                el.alL) {
            l.close();
        }
        el.alL.clear();
    }

    public Object[] executeLuaFunction(ArrayList args, String lua) {
        LuaState L = LuaStateFactory.newLuaState();
        el.alL.add(L);
        L.openLibs();
        for (Object to_ :
                el.globals) {
            TObject to = (TObject) to_;
            L.pushJavaObject(to.searchPropertyByName("value").getPropertyValue());
            L.setGlobal(to.getTclass());
        }
        L.LdoString(lua);
        LuaObject func = L.getLuaObject("m_run");
        Object[] teste = new Object[]{};
        try {
            Log.e("**********Lua ", lua);
            teste = func.call(new Object[]{args}, 1);
        } catch (Exception e) {
            Log.e("*********************", e.getLocalizedMessage());
            Log.e("*********************", e.getMessage());
            for (StackTraceElement ste :
                    e.getStackTrace()) {
                Log.e("*****************", ste.toString());
            }

            e.printStackTrace();
            //el.closeAllState();
        }
        return teste;
    }


    /*public static ArrayList getGlobInstances() {
        return globals;
    }*/

    public void addGlobal(ArrayList al) {
        el.globals.addAll(al);
    }

    public void updateGlobalInstances(final Context c) {
        el.globals.clear();
        el.globals.add(new TObject("context", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(c)));
        }}, new ArrayList<TObject>()));
        el.globals.add(new TObject("parser", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(new Parser())));
        }}, new ArrayList<TObject>()));
        el.globals.add(new TObject("superdialog", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(new SuperDialog())));
        }}, new ArrayList<TObject>()));
        el.globals.add(new TObject("utils", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(new Utils())));
        }}, new ArrayList<TObject>()));
        el.globals.add(new TObject("fh", new ArrayList<Property>() {{
            add(new Property("value", new PropertyValue(new FileHandler())));
        }}, new ArrayList<TObject>()));
    }
}
