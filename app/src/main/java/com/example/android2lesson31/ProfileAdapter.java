package com.example.android2lesson31;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android2lesson31.models.Jobs;
import com.example.android2lesson31.ui.board.BoardAdapter;

import java.util.ArrayList;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private ArrayList<Jobs> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ProfileAdapter( Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addDataToList(ArrayList<Jobs> list) {
        this.list=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textJob, textDesc;
        private LottieAnimationView lottieAnimationView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textJob=itemView.findViewById(R.id.textJob);
            textDesc=itemView.findViewById(R.id.textDesc);
            lottieAnimationView=itemView.findViewById(R.id.lottie1);
        }

        public void onBind(Jobs jobs) {
            textJob.setText(jobs.getProfession());
            textDesc.setText(jobs.getDescription());
            lottieAnimationView.setAnimation(jobs.getImageRaw());

        }
    }
}
