package com.example.fadwasa.stackoverflowapp.Users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fadwasa.stackoverflowapp.Questions.QuestionsActivity;
import com.example.fadwasa.stackoverflowapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<ViewModel> list;
    private Context mContext;
    private Integer id;


    public ListAdapter(Context context, List<ViewModel> list) {
        this.list = list;
        mContext=context;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {

        holder.profileName.setText(list.get(position).getName());
        holder.bronzeBadge.setText(list.get(position).getBronze().toString());
         Glide.with(mContext).load(list.get(position).getProfileImage1()).into(holder.image);
        holder.setIdU(list.get(position).getAccountId());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.bronze_badge)
        TextView bronzeBadge;
        @BindView(R.id.profile_name)
        TextView profileName;
        @BindView(R.id.profile_image)
        ImageView image;
        private Integer idU;

        @OnClick
        void onClick(View view) {
             Bundle bundle = new Bundle();
            Log.d("vall","useridddd555"+idU.toString());

            bundle.putString("accountID",idU.toString() );
            Intent intent = new Intent(view.getContext(), QuestionsActivity.class);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            view.getContext().startActivity(intent);

        }

        public ListItemViewHolder(View itemView ) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.idU=idU;

        }
        void  setIdU(Integer idU){

            this.idU=idU;
        }


    }
}

