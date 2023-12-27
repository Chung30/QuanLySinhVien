package com.example.doanqlsinhvien.Control.Student;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanqlsinhvien.Model.student;
import com.example.doanqlsinhvien.R;

import java.util.ArrayList;



public class GridAdapterStudent extends ArrayAdapter<student> {
    Context mContext;
    ArrayList<student> mGridData;

    public GridAdapterStudent(@NonNull Context context, int resource, @NonNull ArrayList<student> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mGridData = objects;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = new CustomViewStudent(mContext);
        }

        ViewHolderStudent holder = new ViewHolderStudent();

        student item = mGridData.get(position);
        if(item != null) {
            holder.txtName = row.findViewById(R.id.txtStudentName);
            holder.txtDOB = row.findViewById(R.id.txtStudentDOB);
            holder.txtPhone = row.findViewById(R.id.txtStudentPhone);
            holder.txtEmail = row.findViewById(R.id.txtStudentEmail);
            holder.statement = row.findViewById(R.id.txtStudentStatement);

            holder.txtName.setText(item.getName());
            holder.txtDOB.setText(item.getDob());
            holder.txtPhone.setText(item.getPhone());
            holder.txtEmail.setText(item.getEmail());
            if(item.getStatement() == 1) {
                holder.statement.setText("Đang học");
            } else {
                holder.statement.setText("Chưa kích hoạt");
            }
        }
        notifyDataSetChanged();
        return row;
    }
}
