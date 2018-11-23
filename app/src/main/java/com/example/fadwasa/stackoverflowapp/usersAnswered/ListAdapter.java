package com.example.fadwasa.stackoverflowapp.usersAnswered;

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
import com.example.fadwasa.stackoverflowapp.Questions.QuestionsActivity;
import com.example.fadwasa.stackoverflowapp.R;
import com.example.fadwasa.stackoverflowapp.http.apimodel.AOwner;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<AOwner> list;
    private Context mContext;
    private Integer id;

    public ListAdapter(Context context, List<AOwner> list) {
        this.list = list;
        mContext=context;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.usersanswered_list, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {

        holder.profileNameA.setText(list.get(position).getDisplayName());
          Glide.with(mContext).load(list.get(position).getProfileImage()).into(holder.imageA);
        holder.setIdU(list.get(position).getUserId());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile_nameA)
        TextView profileNameA;
        @BindView(R.id.profile_imageA)
        ImageView imageA;
        private Integer idUA;

        @OnClick
        void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("accountID",idUA.toString() );
            Intent intent = new Intent(view.getContext(), QuestionsActivity.class);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            view.getContext().startActivity(intent);

        }

        public ListItemViewHolder(View itemView ) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
        void  setIdU(Integer idUA){

            this.idUA=idUA;
        }
    }
}

