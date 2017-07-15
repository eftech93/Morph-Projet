package com.tunaprojects.morph.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tunaprojects.morph.Controller.View.StartActivityController;
import com.tunaprojects.morph.R;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private StartActivityController sac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        this.sac = new StartActivityController(this);
        this.sac.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.sac.onResume();
    }

    protected void openSession(String appname, ArrayList<String> activities){
        this.sac.openSession(appname, activities);
    }
}