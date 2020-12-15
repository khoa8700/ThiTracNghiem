package com.example.thitracnghiem.activity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thitracnghiem.R;
import com.example.thitracnghiem.model.CustomItem;
import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<CustomItem> {

    public CustomAdapter(@NonNull Context context, ArrayList<CustomItem> customList) {
        super(context,0, customList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout,parent,false);
        }
        CustomItem item=getItem(position);
        TextView spinnerTv=convertView.findViewById(R.id.tvSpinnerLayout);
        if (item!= null) {
            spinnerTv.setText(item.getSpinnerItemName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout,parent,false);
        }
        CustomItem item=getItem(position);
        TextView dropDownTv=convertView.findViewById(R.id.tvDropdownLayout);
        if (item!= null) {
            dropDownTv.setText(item.getSpinnerItemName());
        }
        return convertView;
    }
}
