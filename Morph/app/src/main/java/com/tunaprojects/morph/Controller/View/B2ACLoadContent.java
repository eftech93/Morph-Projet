package com.tunaprojects.morph.Controller.View;

import android.app.Activity;
import android.view.View;

import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Parser.XmlParser;
import com.tunaprojects.morph.Controller.Utility.Utils;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.TObject;
import com.tunaprojects.morph.Model.Session.Session;

import java.util.ArrayList;

/**
 * Created by Esteban Puello on 20/07/2017.
 * Créée par fabrication pure et indirection (indirection parmis la classe B2AController et le LoadType2)
 */

public class B2ACLoadContent {
    private B2ACLoadType2 b2aclt2;
    private Activity activity;
    private XmlParser xp;

    private B2ACLoadContent() {
    }

    public B2ACLoadContent(Activity c, ArrayList<String> views, ArrayList<String> elements, ArrayList<String> containers) {
        this.xp = new XmlParser(containers, elements);
        this.activity = c;
        this.b2aclt2 = new B2ACLoadType2(elements, containers, views, this.activity);
    }

    public ArrayList<View> loadContent(String pageName, int loadType, ArrayList oldData) {
        ArrayList<View> alv = new ArrayList<>();
        Session s = Session.getInstance();
        TObject to = s.getElement("app");
        String filename = null;
        filename = getFilename(pageName, to);
        if (filename != null && loadType == 2) {
            ArrayList<String> als = FileHandler.readFromFile(this.activity, filename);
            String xml = Utils.join(als, "");
            ArrayList<TObject>[] alvca = this.xp.docParser(this.activity, xml);
            alv = this.b2aclt2.loadType2_0(alvca[0], oldData);
        } else {
            SuperDialog.createToastMessage(this.activity, "Activity not found " + pageName);
        }
        return alv;
    }

    private String getFilename(String pageName, TObject to) {
        for (TObject child : to.getChilds()) {
            Property p = child.searchPropertyByName("name");
            if (p != null && p.getPropertyValue().toString().contentEquals(pageName)) {
                Property p2 = child.searchPropertyByName("filename");
                if (p2 != null) {
                    return p2.getPropertyValue().toString();
                }
            }
        }
        return null;
    }
}
