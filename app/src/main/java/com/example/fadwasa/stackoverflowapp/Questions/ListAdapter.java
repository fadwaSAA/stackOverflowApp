package com.example.fadwasa.stackoverflowapp.Questions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.usersAnswered.UsersAnsweredActivity;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<ViewModel> list;
    private Context mContext;

    public ListAdapter(Context context, List<ViewModel> list) {
        this.list = list;
        mContext=context;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {

        holder.questionTitle.setText(list.get(position).getTitle());
       holder.questionsCount.setText(list.get(position).getAnswerCount().toString());

        if(list.get(position).getProfileImage1()!=null && list.get(position).getName()!=null) {
            holder.nameA.setText(list.get(position).getName());

            Glide.with(mContext).load(list.get(position).getProfileImage1()).into(holder.profileImageA);
        }
        holder.setQuestionID(list.get(position).getQuestionID()+"");


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.title)
        TextView questionTitle;

        @BindView(R.id.count)
        TextView questionsCount;

        @BindView(R.id.profile_imageAnswer)
        ImageView profileImageA;

        @BindView(R.id.asweredName)
        TextView nameA;
        String questionID;

        @OnClick
        void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("questionID",questionID.toString() );
            Intent intent = new Intent(view.getContext(), UsersAnsweredActivity.class);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            view.getContext().startActivity(intent);

        }


        public ListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void setQuestionID(String questionID){

          this.questionID=questionID;
        }
    }
}

