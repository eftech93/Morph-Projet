package com.tunaprojects.morph.View.Bases;

import android.os.Bundle;
import com.tunaprojects.morph.Controller.View.Base2ActivityController;
import com.tunaprojects.morph.View.AbstractActivity;

import java.util.ArrayList;

public class Base2Activity extends AbstractActivity {
    private Base2ActivityController b2ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b2ac = new Base2ActivityController(this);
        b2ac.start(getIntent());
    }

    /**
     * This function calls the method newPage of Base2ActivityController
     * @param pageName it's the name of the page that will be instanced
     */
    public void newPage(String pageName) {
        this.b2ac.newPage(pageName);
    }

    /**
     * This method will load the content of the page in the actual page
     * @param pageName it's the name of the page
     * @param oldData it's the data needed to open the page
     */
    public void loadContent(String pageName, ArrayList oldData) {
        this.b2ac.loadContent(pageName);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.b2ac.onRestart();
    }

}
