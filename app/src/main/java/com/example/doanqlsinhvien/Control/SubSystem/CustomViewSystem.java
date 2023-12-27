package com.example.doanqlsinhvien.Control.SubSystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doanqlsinhvien.R;


public class CustomViewSystem extends LinearLayout {

    public TextView txtName_item;
    public ImageView img_item;
    public CustomViewSystem(Context context) {
        super(context);
        LayoutInflater layout = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout.inflate(R.layout.gird_item, this, true);
        txtName_item = (TextView) findViewById(R.id.txtGridName);
        img_item = (ImageView) findViewById(R.id.imageView);
    }
}
