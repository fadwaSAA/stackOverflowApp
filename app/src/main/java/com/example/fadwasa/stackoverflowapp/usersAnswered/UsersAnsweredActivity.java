package com.example.fadwasa.stackoverflowapp.usersAnswered;

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
import com.example.fadwasa.stackoverflowapp.http.AnswersInfoPckge.AOwner;
import com.example.fadwasa.stackoverflowapp.root.App;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAnsweredActivity extends BaseView implements UsersAnsweredActivityMVP.View {

    private final String TAG = UsersAnsweredActivity.class.getName();

    @BindView(R.id.recycler_viewA)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootViewA)
    ViewGroup rootView;

    @Inject
    UsersAnsweredActivityMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private ArrayList<ViewModel> resultList = new ArrayList<>();
    private String questionID;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersanswered_activity);
        Intent intent = getIntent();
        questionID = intent.getStringExtra("questionID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((App) getApplication()).getAnsweredComponenet().injectA(this);
        ButterKnife.bind(this);
        progressBar=new ProgressDialog(this.getApplicationContext());

        presenter.setView(this);

        if(savedInstanceState==null){
            presenter.loadData(questionID);
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
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
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

}
