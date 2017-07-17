package com.tunaprojects.morph;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.tunaprojects.morph.Controller.AsyncCalls.LoadImageTask;
import com.tunaprojects.morph.Controller.File.FileHandler;
import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import com.tunaprojects.morph.View.Bases.test;
import com.tunaprojects.morph.View.StartActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CoordinatorLayout cl = (CoordinatorLayout) findViewById(R.id.mainactivity);
        CoordinatorLayout.LayoutParams cllp = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ScrollView sv = new ScrollView(this);
        cl.addView(sv, cllp);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        TextView tv = new TextView(this);
        tv.setText("hola mundo");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Pattern p = Pattern.compile("[[a*][b+][a*]]+");
                Matcher m = p.matcher("baaaaaaaaaaaaaaaaabaaa");
                boolean b = m.matches();
                if (b == true) {
                    Log.e("Match","There is a sp. character in my string");
                } else {
                    Log.e("Match","There is no sp. char.");
                }
*/
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        TextView tv1 = new TextView(this);
        tv1.setText("Mundo hola");
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, test.class);
                //startActivity(intent);
            }
        });

        Button b = new Button(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> g = FileHandler.readFromFile(MainActivity.this, "systemfiles.dat");
                for (String sr : g) {
                    Log.d("File", "-------------------------------------------------------");
                    Log.d("File", sr);
                }
                g = FileHandler.readFromFile(MainActivity.this, "userdata.dat");
                for (String sr : g) {
                    Log.d("File", "-------------------------------------------------------");
                    Log.d("File", sr);
                }

            }
        });

        EditText et = new EditText(this);
        et.setHint("Hola mundo");
        b.setText("Presioname pls");
        ll.addView(tv);
        ll.addView(tv1);
        ll.addView(b);
        ll.addView(et);
        final String url = "https://upload.wikimedia.org/wikipedia/commons/a/ac/Raus_-_Roy_Puello_2.jpg";
        ImageView ov = new ImageView(MainActivity.this);
        LoadImageTask lit = new LoadImageTask(ov);
        lit.execute(url);
        ov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperDialog.openChildViewAsDialog(MainActivity.this, view, this);
                view.setTag("Id");
                SuperDialog.createToastMessage(MainActivity.this, view.getTag().toString() + "");
            }
        });
        ll.addView(ov);

    }


}
