package com.example.fadwasa.stackoverflowapp.Questions;

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
import com.example.fadwasa.stackoverflowapp.root.App;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class QuestionsActivity extends BaseView{


    @BindView(R.id.recycler_view1)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView1)
    ViewGroup rootView;



    private ListAdapter listAdapter;
    private List<QuestionsViewModel> resultList = new ArrayList<>();
    String accountID;
    ProgressDialog progressBar;
    private  QuestionsViewModel mViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_activity);
        Intent intent = getIntent();
          accountID = intent.getStringExtra("accountID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((App) getApplication()).getQComponent().injectQ(this);
        ButterKnife.bind(this);
        progressBar=new ProgressDialog(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        progressBar=new ProgressDialog(this.getApplicationContext());
         initAdapter();
        loadD();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        listAdapter.notifyDataSetChanged();
    }


    public void updateData(List<QuestionsViewModel>items) {
        resultList =items;
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
        recyclerView.addItemDecoration(new com.example.fadwasa.stackoverflowapp.usersAnswered.DividerItemDecoration(this));
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
        mViewModel.loadData(accountID).observe(this, new android.arch.lifecycle.Observer<List<QuestionsViewModel>>() {
            @Override
            public void onChanged(@Nullable List<QuestionsViewModel> Items) {
                updateData(Items);
                listAdapter.updateData(Items);

            }
        });

    }

}
