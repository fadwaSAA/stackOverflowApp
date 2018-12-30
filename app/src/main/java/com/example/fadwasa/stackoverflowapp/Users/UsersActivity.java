package com.example.fadwasa.stackoverflowapp.Users;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
 import android.view.ViewGroup;
import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.baseMVP.BaseView;
import com.example.fadwasa.stackoverflowapp.http.UsersInfoPckg.Item;
import com.example.fadwasa.stackoverflowapp.root.App;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class UsersActivity extends BaseView{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView)
    ViewGroup rootView;



    private ListAdapter listAdapter;
    private List<Item> resultList = new ArrayList<>();
    ProgressDialog progressBar;
    private  UsersViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);
        ((App) getApplication()).getUserApplicationComponent().inject(this);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        progressBar = new ProgressDialog(this);
         super.ShowProgressBar();

        initAdapter();
        loadD();
        super.hideProgressBar();


    }

     public void updateData(List<Item> items) {
        resultList = items;
        listAdapter.notifyItemInserted(resultList.size() - 1);
    }

    @Override
    public void showSnackbar(String msg,ViewGroup rootView1) {
        rootView1=rootView;
        super.showSnackbar(msg,rootView1);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        listAdapter.notifyDataSetChanged();}



    private void initAdapter() {
        listAdapter = new ListAdapter(this.getApplicationContext(),resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void loadD(){
        mViewModel.loadData().observe(this, new android.arch.lifecycle.Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> Items) {
                 updateData(Items);
                listAdapter.updateData(Items);


            }

        });

    }


}
