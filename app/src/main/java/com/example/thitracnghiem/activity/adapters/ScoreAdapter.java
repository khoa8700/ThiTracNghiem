package com.example.thitracnghiem.activity.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thitracnghiem.R;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private View v;
    List<Double> scores;
    public ScoreAdapter(List<Double> scores){
        this.scores=scores;
    }
    @NonNull
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.score_in_line,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ViewHolder holder, int position) {
        Double score=scores.get(position);
        holder.setColor(score);
        holder.tvName.setText("#");
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvScore,tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            tvScore=v.findViewById(R.id.tv_score);
            tvName=v.findViewById(R.id.tv_name);
        }
        public void setColor(Double score){
            if(score<=5){
                tvScore.setTextColor(Color.GRAY);
            }
            else if(score<=7){
                tvScore.setTextColor(Color.YELLOW);
            }
            else{
                tvScore.setTextColor(Color.RED);
            }
            tvScore.setText(score+"");
        }
    }
}
