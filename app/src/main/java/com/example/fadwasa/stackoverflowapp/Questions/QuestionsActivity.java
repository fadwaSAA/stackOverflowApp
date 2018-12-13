package com.example.fadwasa.stackoverflowapp.Questions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.baseMVP.BaseView;
import com.example.fadwasa.stackoverflowapp.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends BaseView implements QuestionsActivityMVP.View {

    private final String TAG = QuestionsActivity.class.getName();

    @BindView(R.id.recycler_view1)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView1)
    ViewGroup rootView;

    @Inject
    QuestionsActivityMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private ArrayList<ViewModel> resultList = new ArrayList<>();
    String accountID;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questions_activity);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        accountID = intent.getStringExtra("accountID");

        ((App) getApplication()).getQComponent().injectQ(this);

        ButterKnife.bind(this);
        progressBar=new ProgressDialog(this.getApplicationContext());
        presenter.setView(this);

        if (savedInstanceState == null){
            presenter.loadData(accountID);
            initAdapter();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.rxUnsubscribe();
         listAdapter.notifyDataSetChanged();
    }


    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        listAdapter.notifyItemInserted(resultList.size() - 1);
    }

    @Override
    public void showSnackbar(String msg,ViewGroup rootView1) {
        rootView1=rootView;
        super.showSnackbar(msg,rootView1);
     }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        resultList = savedInstanceState.getParcelableArrayList("viewmodelList");
         initAdapter();
    }

    private void initAdapter() {
        listAdapter = new ListAdapter(this.getApplicationContext(),resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

     @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList("viewmodelList",   resultList);
         super.onSaveInstanceState(savedInstanceState);}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
