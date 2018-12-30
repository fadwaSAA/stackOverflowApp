package com.example.fadwasa.stackoverflowapp.baseMVP;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;


public class BaseView extends AppCompatActivity  {
    ProgressDialog progressBar;
     public void ShowProgressBar() {

         progressBar=new ProgressDialog(this);
         if (progressBar != null && progressBar.isShowing()) {
            progressBar.setMessage("Loading...");
            Log.d("jjj","showinggg");
        } else {
            Log.d("jjj","showinggg2"+progressBar.isShowing());

            progressBar.setIndeterminate(true);
            progressBar.setMessage("Loading...");
            progressBar.setCancelable(false);

            try {
                progressBar.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
     public void hideProgressBar() {
        try {

            if (progressBar != null && progressBar.isShowing()) {
                progressBar.dismiss();
                progressBar.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void showSnackbar(String msg, ViewGroup rootView) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).show();
    }
}
