package com.tunaprojects.morph.View;

import android.support.v7.app.AppCompatActivity;

import com.tunaprojects.morph.Controller.Dialog.SuperDialog;

/**
 * Created by Esteban Puello on 7/07/2017.
 */

public abstract class AbstractActivity extends AppCompatActivity {

    /**
     * When the activity is killed with a message, it opens the message as toast message on its parent
     * @param message Message to be display
     */
    public void finish(String message){
        this.finish();
        SuperDialog.createToastMessage(this.getParent(), message);
    }
}
