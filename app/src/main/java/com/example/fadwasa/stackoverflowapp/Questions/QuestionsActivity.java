package com.example.fadwasa.stackoverflowapp.Questions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends AppCompatActivity implements QuestionsActivityMVP.View {

    private final String TAG = QuestionsActivity.class.getName();

    @BindView(R.id.recycler_view1)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView1)
    ViewGroup rootView;

    @Inject
    QuestionsActivityMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private List<ViewModel> resultList = new ArrayList<>();
    String accountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questions_activity);
        Intent intent = getIntent();
          accountID = intent.getStringExtra("accountID");
         ((App) getApplication()).getQComponent().injectQ(this);

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
        Log.d("vall","useridddd000"+accountID);

        presenter.loadData(accountID);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxUnsubscribe();
        resultList.clear();
        listAdapter.notifyDataSetChanged();


    }


    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        listAdapter.notifyItemInserted(resultList.size() - 1);
    }

    @Override
    public void showSnackbar(String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).show();

    }
}
