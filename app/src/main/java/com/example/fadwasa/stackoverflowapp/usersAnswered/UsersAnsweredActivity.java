package com.example.fadwasa.stackoverflowapp.usersAnswered;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;
import com.example.fadwasa.stackoverflowapp.root.App;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAnsweredActivity extends AppCompatActivity implements UsersAnsweredActivityMVP.View {

    private final String TAG = UsersAnsweredActivity.class.getName();

    @BindView(R.id.recycler_viewA)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootViewA)
    ViewGroup rootView;

    @Inject
    UsersAnsweredActivityMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private List<AOwner> resultList = new ArrayList<>();
    private String questionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersanswered_activity);
        Intent intent = getIntent();
        questionID = intent.getStringExtra("questionID");
        ((App) getApplication()).getComponent().injectA(this);

        ButterKnife.bind(this);

        listAdapter = new ListAdapter(this.getApplicationContext(),resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.loadData(questionID);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxUnsubscribe();
        resultList.clear();
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void updateData(AOwner viewModel) {
        resultList.add(viewModel);
        listAdapter.notifyItemInserted(resultList.size() - 1);
    }

    @Override
    public void showSnackbar(String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).show();

    }
}