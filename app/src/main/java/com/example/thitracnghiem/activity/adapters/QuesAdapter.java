package com.example.thitracnghiem.activity.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thitracnghiem.R;
import com.example.thitracnghiem.model.Question;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class QuesAdapter extends RecyclerView.Adapter<QuesAdapter.ViewHolder> {
    private List<Question> mainList;
    private List<String> ans;
    private View v;
    private  ArrayList<Boolean> mCheckedState;
    public QuesAdapter(List<Question> mainList,List<String> ans,ArrayList<Boolean> mCheckedState){
        this.ans=ans;
        this.mainList=mainList;
        this.mCheckedState=mCheckedState;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_line,parent,false));
    }
    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image, Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        String image=mainList.get(position).getQ();
        Bitmap bm=StringToBitMap(image);
        holder.img.setImageBitmap(bm);
        holder.quesIndex.setText("Câu "+(position+1)+":");

        if(mCheckedState.get(position)==false){
            holder.btnA.setChecked(false);
            holder.btnB.setChecked(false);
            holder.btnC.setChecked(false);
            holder.btnD.setChecked(false);
        }
        else{
            String cur=ans.get(position);
            if(cur.equals("A")){
                holder.btnA.setChecked(true);
            }
            if(cur.equals("B")){
                holder.btnB.setChecked(true);
            }
            if(cur.equals("C")){
                holder.btnC.setChecked(true);
            }
            if(cur.equals("D")){
                holder.btnD.setChecked(true);
            }
        }
        holder.btnA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ans.set(position,compoundButton.getText().toString());
                    mCheckedState.set(position,true);
                }
            }
        });
        holder.btnB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ans.set(position,compoundButton.getText().toString());
                    mCheckedState.set(position,true);
                }
            }
        });
        holder.btnC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ans.set(position,compoundButton.getText().toString());
                    mCheckedState.set(position,true);
                }
            }
        });
        holder.btnD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ans.set(position,compoundButton.getText().toString());
                    mCheckedState.set(position,true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RadioButton btnA,btnB,btnC,btnD;
        private ImageView img;
        private TextView quesIndex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            btnA=v.findViewById(R.id.btn_a);
            btnB=v.findViewById(R.id.btn_b);
            btnC=v.findViewById(R.id.btn_c);
            btnD=v.findViewById(R.id.btn_d);
            img=v.findViewById(R.id.imamge);
            quesIndex=v.findViewById(R.id.quesIndex);
        }
    }
}

