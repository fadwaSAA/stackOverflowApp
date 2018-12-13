package com.example.fadwasa.stackoverflowapp.Users;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.baseMVP.BaseView;
import com.example.fadwasa.stackoverflowapp.root.App;
import java.util.ArrayList;
 import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersActivity extends BaseView  implements UsersActivityMVP.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView)
    ViewGroup rootView;

    @Inject
    UsersActivityMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private ArrayList<ViewModel> resultList = new ArrayList<>();
      ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);
        ((App) getApplication()).getUserApplicationComponent().inject(this);
        ButterKnife.bind(this);
        progressBar = new ProgressDialog(this);

        presenter.setView(this);
        if (savedInstanceState == null){
            presenter.loadData();
            initAdapter();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.rxUnsubscribe();
         listAdapter.notifyDataSetChanged();
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
         super.onSaveInstanceState(savedInstanceState);
    }

}
