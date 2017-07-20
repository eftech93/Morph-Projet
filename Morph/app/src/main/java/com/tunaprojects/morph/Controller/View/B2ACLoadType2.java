package com.tunaprojects.morph.Controller.View;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.tunaprojects.morph.Controller.AsyncCalls.LoadImageTask;
import com.tunaprojects.morph.Controller.Parser.ViewPreparer;
import com.tunaprojects.morph.Controller.Utility.ExecuteLua;
import com.tunaprojects.morph.Model.Instance.Property;
import com.tunaprojects.morph.Model.Instance.TObject;
import com.tunaprojects.morph.R;

import java.util.ArrayList;

/**
 * Created by Esteban Puello on 5/07/2017.
 * Créé pa fabrication pure
 */

class B2ACLoadType2 {
    private final ArrayList<String> elements;
    private final ArrayList<String> containers;
    private final ArrayList<String> view;
    private final Activity activity;
    private final ArrayList<View> alviews;
    public B2ACLoadType2(ArrayList<String> elements, ArrayList<String> containers, ArrayList<String> view, Activity activity){
        this.elements = elements;
        this.containers = containers;
        this.view = view;
        this.activity = activity;
        this.alviews = new ArrayList<>();
    }

    protected ArrayList<View> loadType2_0(ArrayList<TObject> alvca0, ArrayList oldData) {
        DrawerLayout dr = (DrawerLayout) this.activity.findViewById(R.id.drawer_layout);
        if (!alvca0.isEmpty()) {
            LinearLayout ll = (LinearLayout) dr.findViewById(R.id.content_base);
            ll.removeAllViews();
            this.alviews.clear();
            ViewPreparer vp = ViewPreparer.getInstance();
            for (TObject vc :
                    alvca0) {
                if (this.view.contains(vc.getTclass())) {
                    View v = vp.generateBaseElement(this.activity, vc, oldData);
                    this.alviews.add(v);
                    ll.addView(v);
                }
            }
        }
        return alviews;
    }
    protected ArrayList<View> loadType2(final ArrayList<TObject> alvca0, final ArrayList<TObject> alvca2, final ArrayList<TObject> alvca3, ArrayList oldData) {
        this.activity.setContentView(R.layout.activity_navegation_base);
        Toolbar toolbar = (Toolbar) this.activity.findViewById(R.id.toolbar);
        DrawerLayout dr = (DrawerLayout) this.activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this.activity, dr, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dr.setDrawerListener(toggle);
        toggle.syncState();
        loadType2_0(alvca0, oldData);
        loadType2_2(alvca2, oldData);
        loadType2_3(alvca3, oldData);
        return alviews;
    }

    private void loadType2_2(ArrayList<TObject> alvca2, ArrayList oldData) {
        DrawerLayout dr = (DrawerLayout) this.activity.findViewById(R.id.drawer_layout);
        NavigationView nv = (NavigationView) dr.findViewById(R.id.nav_view);
        LinearLayout ll = new LinearLayout(this.activity);
        ll.setBackgroundColor(Color.DKGRAY);
        ViewPreparer vp = ViewPreparer.getInstance();
        for (TObject vc : alvca2) {
            ll.addView(vp.generateBaseElement(this.activity, vc, oldData));
        }
        nv.addHeaderView(ll);
        ll.getLayoutParams().height = 350;
    }

    private void loadType2_3(ArrayList<TObject> alvca3, final ArrayList oldData) {
        DrawerLayout dr = (DrawerLayout) this.activity.findViewById(R.id.drawer_layout);
        NavigationView nv = (NavigationView) dr.findViewById(R.id.nav_view);
        this.activity.getMenuInflater().inflate(R.menu.activity_navegation_base_drawer, nv.getMenu());
        Menu m = nv.getMenu();
        m.clear();
        if (!alvca3.isEmpty()) {
            for (TObject vc : alvca3) {
                // Convertir este codigo a lua
                checkChild3(oldData, m, vc);
                //m.addSubMenu(" ").add(" ");
                //fin de convertir en convertir en lua
            }
        }
    }

    private void checkChild3(final ArrayList oldData, Menu m, TObject vc) {
        for (TObject child : vc.getChilds()) {
            MenuItem mi = m.add("");
            for (final Property p : child.getProperties()) {
                checkProperty3(oldData, mi, p);
            }
        }
    }

    private void checkProperty3(final ArrayList oldData, MenuItem mi, final Property p) {
        if (p.getPropertyName().contentEquals("text")) {
            mi.setTitle(p.getPropertyValue().toString());
        } else if (p.getPropertyName().contentEquals("onClick")) {
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    ExecuteLua el = ExecuteLua.getInstance();
                    el.updateGlobalInstances(activity);
                    ArrayList objs_ = new ArrayList();
                    objs_.add(oldData);
                    Object[] rt = el.executeLuaFunction(objs_, p.getPropertyValue().toString());
                    return true;
                }
            });
        } else if (p.getPropertyName().contentEquals("urlicon")) {
            LoadImageTask lit = new LoadImageTask(this.activity, mi);
            lit.execute(p.getPropertyValue().toString());
        }
    }


}
