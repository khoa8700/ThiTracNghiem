package com.example.thitracnghiem.activity.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thitracnghiem.R;
import com.example.thitracnghiem.model.Question;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class QuesPracAdapter extends RecyclerView.Adapter<QuesPracAdapter.ViewHolder> {
    private List<Question> mainList;
    private List<String> answ;
    private ArrayList<String> index;
    private View v;
    private  ArrayList<Boolean> mCheckedState;
    public QuesPracAdapter(List<Question> mainList, List<String> ans, ArrayList<String> index, ArrayList<Boolean> mCheckedState){
        this.answ=ans;
        this.mainList=mainList;
        this.index=index;
        this.mCheckedState=mCheckedState;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_line_prac,parent,false));
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        String image=mainList.get(position).getQ();
        Bitmap bm=StringToBitMap(image);
        holder.img.setImageBitmap(bm);
        String ans=mainList.get(position).getA();
        holder.tvAns.setText(ans);
        holder.quesIndex.setText(index.get(position));
        boolean expandMode=mCheckedState.get(position);
        if(expandMode) holder.expand.setVisibility(View.VISIBLE);
        else holder.expand.setVisibility(View.GONE);
        final String cur = mainList.get(position).getA();
        if(mCheckedState.get(position)==false){
            holder.btnA.setSelected(false);
            holder.btnB.setSelected(false);
            holder.btnC.setSelected(false);
            holder.btnD.setSelected(false);
        }
        else {
            String cansw = answ.get(position);
            if(cur.equals("A")){
                holder.btnA.setSelected(true);
                holder.btnA.setBackgroundResource(R.color.colorGreen);
                if(cansw.equals("B"))  holder.btnB.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("C"))  holder.btnC.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("D"))  holder.btnD.setBackgroundResource(R.color.colorRed);
            }
            if(cur.equals("B")){
                holder.btnB.setSelected(true);
                holder.btnB.setBackgroundResource(R.color.colorGreen);
                if(cansw.equals("A"))  holder.btnA.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("C"))  holder.btnC.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("D"))  holder.btnD.setBackgroundResource(R.color.colorRed);
            }
            if(cur.equals("C")){
                holder.btnC.setSelected(true);
                holder.btnC.setBackgroundResource(R.color.colorGreen);
                if(cansw.equals("B"))  holder.btnB.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("A"))  holder.btnA.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("D"))  holder.btnD.setBackgroundResource(R.color.colorRed);
            }
            if(cur.equals("D")){
                holder.btnD.setSelected(true);
                holder.btnD.setBackgroundResource(R.color.colorGreen);
                if(cansw.equals("B"))  holder.btnB.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("C"))  holder.btnC.setBackgroundResource(R.color.colorRed);
                if(cansw.equals("A"))  holder.btnA.setBackgroundResource(R.color.colorRed);
            }
        }


        holder.btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckedState.set(position,true);
                holder.expand.setVisibility(View.VISIBLE);
                answ.set(position,"A");

                if(cur.equals("A")) holder.btnA.setBackgroundResource(R.color.colorGreen);
                else holder.btnA.setBackgroundResource(R.color.colorRed);

                holder.btnB.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnC.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnD.setBackgroundResource(android.R.drawable.btn_default);

                if(cur.equals("B"))  holder.btnB.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("C"))  holder.btnC.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("D"))  holder.btnD.setBackgroundResource(R.color.colorGreen);
            }
        });
        holder.btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckedState.set(position,true);
                holder.expand.setVisibility(View.VISIBLE);
                answ.set(position,"B");

                if(cur.equals("B")) holder.btnB.setBackgroundResource(R.color.colorGreen);
                else holder.btnB.setBackgroundResource(R.color.colorRed);

                holder.btnA.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnC.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnD.setBackgroundResource(android.R.drawable.btn_default);

                if(cur.equals("A"))  holder.btnA.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("C"))  holder.btnC.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("D"))  holder.btnD.setBackgroundResource(R.color.colorGreen);

            }
        });
        holder.btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckedState.set(position,true);
                holder.expand.setVisibility(View.VISIBLE);
                answ.set(position,"C");

                if(cur.equals("C")) holder.btnC.setBackgroundResource(R.color.colorGreen);
                else holder.btnC.setBackgroundResource(R.color.colorRed);

                holder.btnB.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnA.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnD.setBackgroundResource(android.R.drawable.btn_default);

                if(cur.equals("B"))  holder.btnB.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("A"))  holder.btnA.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("D"))  holder.btnD.setBackgroundResource(R.color.colorGreen);
            }
        });
        holder.btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckedState.set(position,true);
                holder.expand.setVisibility(View.VISIBLE);
                answ.set(position,"D");

                if(cur.equals("D")) holder.btnD.setBackgroundResource(R.color.colorGreen);
                else holder.btnD.setBackgroundResource(R.color.colorRed);

                holder.btnB.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnC.setBackgroundResource(android.R.drawable.btn_default);
                holder.btnA.setBackgroundResource(android.R.drawable.btn_default);

                if(cur.equals("B"))  holder.btnB.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("C"))  holder.btnC.setBackgroundResource(R.color.colorGreen);
                if(cur.equals("A"))  holder.btnA.setBackgroundResource(R.color.colorGreen);

            }
        });

    }


    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnA,btnB,btnC,btnD;
        private ImageView img;
        private TextView quesIndex;
        private TextView tvAns;
        private LinearLayout expand;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            btnA=v.findViewById(R.id.btn_a);
            btnB=v.findViewById(R.id.btn_b);
            btnC=v.findViewById(R.id.btn_c);
            btnD=v.findViewById(R.id.btn_d);
            img=v.findViewById(R.id.imamge);
            quesIndex=v.findViewById(R.id.quesIndex);
            tvAns=v.findViewById(R.id.tv_ans);
            expand=v.findViewById(R.id.layout_expand);
        }
    }
}

