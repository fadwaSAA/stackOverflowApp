package com.example.fadwasa.stackoverflowapp.usersAnswered;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
 import android.view.MenuItem;
import android.view.ViewGroup;
import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.baseMVP.BaseView;
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AItem;
import com.example.fadwasa.stackoverflowapp.root.App;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class UsersAnsweredActivity extends BaseView  {


    @BindView(R.id.recycler_viewA)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootViewA)
    ViewGroup rootView;


    private ListAdapter listAdapter;
    private List<AItem> resultList = new ArrayList<>();
    private String questionID;
    ProgressDialog progressBar;
    private  AnsweredViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersanswered_activity);
        Intent intent = getIntent();
        questionID = intent.getStringExtra("questionID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((App) getApplication()).getAnsweredComponenet().injectA(this);
        ButterKnife.bind(this);
         mViewModel = ViewModelProviders.of(this).get(AnsweredViewModel.class);
        progressBar=new ProgressDialog(this.getApplicationContext());
         loadD();
        initAdapter();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         listAdapter.notifyDataSetChanged();
    }
     public void updateData(List <AItem> list) {
        resultList=list;
        listAdapter.notifyItemInserted(resultList.size() - 1);
    }

    @Override
    public void showSnackbar(String msg,ViewGroup rootView1) {
        rootView1=rootView;
        super.showSnackbar(msg,rootView1);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadD(){
        mViewModel.loadData(questionID).observe(this, new android.arch.lifecycle.Observer<List<AItem>>() {
            @Override
            public void onChanged(@Nullable List<AItem> aItems) {
                  updateData(aItems);
                listAdapter.updateData(aItems);

            }
        });

    }

}
