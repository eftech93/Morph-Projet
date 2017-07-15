package com.tunaprojects.morph.Controller.View;

import android.app.Activity;
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

    protected void loadType1(ArrayList<TObject> alvca, ArrayList oldData) {
        this.activity.setContentView(R.layout.activity_base2);
        LinearLayout rl = (LinearLayout) this.activity.findViewById(R.id.activity_base2);
        LinearLayout ll = (LinearLayout) rl.findViewById(R.id.content_base);
        for (TObject vc :
                alvca) {
            if (this.view.contains(vc.getTclass())) {
                ViewPreparer vp = ViewPreparer.getInstance();
                ll.addView(vp.generateBaseElement(this.activity, vc, oldData));
            }
        }
    }

}
