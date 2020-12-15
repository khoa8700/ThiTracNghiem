package com.example.thitracnghiem.activity.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thitracnghiem.R;
//import com.example.thitracnghiem.SetupInfoActivity;
import com.example.thitracnghiem.model.HighScore;
import com.example.thitracnghiem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private View v;
    List<HighScore> scores;
    public ScoreAdapter(List<HighScore> scores
    ){
        this.scores=scores;
    }
    @NonNull
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.score_in_line,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ScoreAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        HighScore score=scores.get(position);
        holder.setColor(score);
        FirebaseFirestore firebaseFirestore;
        final FirebaseAuth firebaseAuth;
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore.collection("Info").document(score.getId())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null){
                            if(value.exists()){
                                User user=new User(value.get("name").toString(),Integer.parseInt(value.get("age").toString()),value.get("school").toString(),value.get("classs").toString());
                                holder.tvName.setText(user.getName()+":");
                            }
                        }
                    }
                });
        if(score.getId().equals( firebaseAuth.getCurrentUser().getUid() ) ) holder.tvScoreCard.setBackgroundResource(R.color.colorWhite);
        else holder.tvScoreCard.setBackgroundResource(0);
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvScore,tvName;
        private LinearLayout tvScoreCard;
        DecimalFormat formater = new DecimalFormat("0.00");

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            tvScore=v.findViewById(R.id.tv_score);
            tvName=v.findViewById(R.id.tv_name);
            tvScoreCard=v.findViewById(R.id.tv_scoreCard);
        }
        public void setColor(HighScore score){
            if(score.getDiem()<=5){
                tvScore.setTextColor(Color.BLACK);
            }
            else if(score.getDiem()<8){
                tvScore.setTextColor(Color.YELLOW);
            }
            else{
                tvScore.setTextColor(Color.RED);
            }
            tvScore.setText(formater.format(score.getDiem())+"");
        }
    }
}
