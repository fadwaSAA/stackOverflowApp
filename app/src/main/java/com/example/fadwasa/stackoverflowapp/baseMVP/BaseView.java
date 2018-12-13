package com.example.fadwasa.stackoverflowapp.baseMVP;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public class BaseView extends AppCompatActivity implements BaseActivityMVP.View {
    ProgressDialog progressBar;
    @Override
    public void ShowProgressBar() {

         progressBar=new ProgressDialog(this);
         if (progressBar != null && progressBar.isShowing()) {
            progressBar.setMessage("Loading...");
         } else {

            progressBar.setIndeterminate(true);
            progressBar.setMessage("Loading...");
            progressBar.setCancelable(false);

             try {
                progressBar.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }}}

    @Override
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

    @Override
    public void showSnackbar(String msg, ViewGroup rootView) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).show();
    }
}
