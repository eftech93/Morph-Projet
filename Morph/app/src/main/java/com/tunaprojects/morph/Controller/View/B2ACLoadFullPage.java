package com.tunaprojects.morph.Controller.View;

import android.app.Activity;
import android.view.View;

import com.tunaprojects.morph.Controller.Parser.XmlParser;
import com.tunaprojects.morph.Model.Instance.TObject;

import java.util.ArrayList;

/**
 * Created by Esteban Puello on 20/07/2017.
 * Créée par fabrication pure et indirection (indirection parmis la classe B2AController et les LoadTypes -1 et 2-)
 */

public class B2ACLoadFullPage {
    private B2ACLoadType1 b2aclt1;
    private B2ACLoadType2 b2aclt2;
    private Activity activity;
    private XmlParser xp;
    private int loadType;

    private B2ACLoadFullPage() {

    }

    public B2ACLoadFullPage(Activity c, ArrayList<String> views, ArrayList<String> elements, ArrayList<String> containers) {
        this.activity = c;
        this.b2aclt1 = new B2ACLoadType1(views, this.activity);
        this.b2aclt2 = new B2ACLoadType2(elements, containers, views, this.activity);
        this.xp = new XmlParser(containers, elements);
    }

    public ArrayList<View> loadFullPage(String xml, ArrayList oldData) {
        ArrayList<View> alv = new ArrayList<>();
        ArrayList<TObject>[] alvca = this.xp.docParser(this.activity, xml);
        if (alvca.length == 0 || alvca[0].isEmpty() && alvca[1].isEmpty() && alvca[2].isEmpty() && alvca[3].isEmpty()) {
            //this.activity.finish("There's nothing to be loaded");
            this.activity.finish();
        }
        //0 - content
        //1 - actionbar
        //2 - left bar top
        //3 - left bar bottom
        if (!alvca[0].isEmpty() && alvca[2].isEmpty() && alvca[3].isEmpty()) {
            alv = this.b2aclt1.loadType1(alvca[0], oldData);
            this.loadType = 1;
        } else if (!alvca[2].isEmpty() && !alvca[3].isEmpty()) {
            //quand une des views est un groupview il faut obtenir toutes les elements dans le goupview
            //ça manque
            alv.addAll(this.b2aclt2.loadType2(alvca[0], alvca[2], alvca[3], oldData));
            this.loadType = 2;
        }
        //ExecuteLua.closeAllState();
        return alv;
    }

    public int getLoadType() {
        return this.loadType;
    }
}
