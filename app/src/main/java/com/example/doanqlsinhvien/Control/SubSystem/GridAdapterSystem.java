package com.example.doanqlsinhvien.Control.SubSystem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanqlsinhvien.Model.subSystem;
import com.example.doanqlsinhvien.R;

import java.util.ArrayList;



public class GridAdapterSystem extends ArrayAdapter<subSystem> {
    Context mContext;
    ArrayList<subSystem> mGridData;
    public GridAdapterSystem(@NonNull Context context, int resource, @NonNull ArrayList<subSystem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mGridData = objects;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = new CustomViewSystem(mContext);
        }

        ViewHolderSystem holder = new ViewHolderSystem();

        subSystem item = mGridData.get(position);
        if(item != null) {
            holder.txtName_item = (TextView) row.findViewById(R.id.txtGridName);
            holder.img_item = (ImageView) row.findViewById(R.id.imageView);

            holder.txtName_item.setText(item.getName());
            holder.img_item.setImageResource(item.getImg());
        }

        return row;
    }
}
