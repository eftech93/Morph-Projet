package com.tunaprojects.morph.Controller.View;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.tunaprojects.morph.Controller.Parser.ViewPreparer;
import com.tunaprojects.morph.Model.Instance.TObject;
import com.tunaprojects.morph.R;
import java.util.ArrayList;

/**
 * Created by Esteban Puello on 5/07/2017.
 */

class B2ACLoadType1 {
    private final ArrayList<String> view;
    private final Activity activity;

    protected B2ACLoadType1(ArrayList<String> view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    protected ArrayList<View> loadType1(ArrayList<TObject> alvca, ArrayList oldData) {
        ArrayList<View> alv = new ArrayList<>();
        this.activity.setContentView(R.layout.activity_base2);
        LinearLayout rl = (LinearLayout) this.activity.findViewById(R.id.activity_base2);
        LinearLayout ll = (LinearLayout) rl.findViewById(R.id.content_base);
        ViewPreparer vp = ViewPreparer.getInstance();
        for (TObject vc :
                alvca) {
            if (this.view.contains(vc.getTclass())) {
                View v = vp.generateBaseElement(this.activity, vc, oldData);
                alv.add(v);
                ll.addView(v);
            }
        }
        return alv;
    }

}
