package vn.itplus.doanqlsinhvien.Control.MarkStudent;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Model.result;
import vn.itplus.doanqlsinhvien.R;

public class GridAdapterMarkStudent extends ArrayAdapter<result> {
    Context mContext;
    ArrayList<result> mGridData;
    public GridAdapterMarkStudent(@NonNull Context context, int resource, @NonNull ArrayList<result> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mGridData = objects;
    }
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = new CustomViewMarkStudent(mContext);
        }

        ViewHolderMarkStudent holder = new ViewHolderMarkStudent();

        result item = mGridData.get(position);
        if(item != null) {
            holder.txtName = row.findViewById(R.id.txtStudentName);
            holder.txtScore1 = row.findViewById(R.id.txtScore1);
            holder.txtScore2 = row.findViewById(R.id.txtScore2);
            holder.txtFinalScore = row.findViewById(R.id.txtFinalScore);
            holder.txtResultStatement = row.findViewById(R.id.txtResultStatement);

            holder.txtName.setText(item.getNameStudent());
            holder.txtScore1.setText("Điểm kiểm tra 1: " + item.getScore1());
            holder.txtScore2.setText("Điểm kiểm tra 2: " + item.getScore2());
            holder.txtFinalScore.setText("Điểm kiểm tra cuối: " + item.getFinalScore());
            if(item.getrStatement() == 1) holder.txtResultStatement.setText("Hoàn thành");
            else holder.txtResultStatement.setText("Chưa hoàn thành");
        }
        notifyDataSetChanged();

        return row;
    }
}
