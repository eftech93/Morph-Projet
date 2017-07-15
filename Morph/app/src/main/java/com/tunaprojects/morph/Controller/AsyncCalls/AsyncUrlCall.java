package com.tunaprojects.morph.Controller.AsyncCalls;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tunaprojects.morph.Controller.Parser.Parser;
import com.tunaprojects.morph.Controller.Utility.ExecuteLua;
import com.tunaprojects.morph.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Esteban Puello on 1/12/2016.
 */

public class AsyncUrlCall extends AsyncTask<String, Void, String> {

    private String lua = null;
    private ArrayList al = null;
    private ProgressDialog pd;
    private Context context;

   /* public AsyncUrlCall(String lua, ArrayList al) {
        this.lua = lua;
        this.al = al;
    }
*/
    public AsyncUrlCall(Context context, String lua, ArrayList al) {
        this.pd = new ProgressDialog(context);
        this.lua = lua;
        this.al = al;
        this.context = context;
    }

    public void runthis(ArrayList args) {
        execute(Parser.parserArrayListToArrayString(args));
    }

    @Override
    protected void onPreExecute() {
        if (this.pd != null) {
            this.pd = ProgressDialog.show(context, "", context.getString(R.string.please_wait), false);
        }
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpsURLConnection conn = null;
        try {
            url = new URL(urls[0]);
            //Log.e("Async Url", urls[0]);
            conn = (HttpsURLConnection) url.openConnection();
            if (urls.length > 1) {
                StringBuilder params = new StringBuilder();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                for (int i = 1; i < urls.length; i++) {
                    params.append(urls[i]);
                    if (i != urls.length - 1) {
                        params.append("&");
                    }
                }
                //Log.e("Async Params", params.toString());
                wr.writeBytes(params.toString());
                wr.flush();
                wr.close();
            }
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            //Log.e("Result", result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.pd != null) {
            this.pd.dismiss();
        }
        //Log.e("Async PostExecute", result);
        //Log.e("Async Lua", this.lua);
        al.add(0, result);
        ExecuteLua el = ExecuteLua.getInstance();
        el.updateGlobalInstances(this.context);
        el.executeLuaFunction(al, this.lua);
    }
}