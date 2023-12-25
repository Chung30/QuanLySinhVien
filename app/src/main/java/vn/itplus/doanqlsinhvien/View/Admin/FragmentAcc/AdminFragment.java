package vn.itplus.doanqlsinhvien.View.Admin.FragmentAcc;

import static vn.itplus.doanqlsinhvien.R.id.fabAdd_Admin;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.itplus.doanqlsinhvien.Control.Account.GridAdapterAcc;
import vn.itplus.doanqlsinhvien.Data.SQLiteAdapter;
import vn.itplus.doanqlsinhvien.Model.account;
import vn.itplus.doanqlsinhvien.Model.teacher;
import vn.itplus.doanqlsinhvien.R;

public class AdminFragment extends Fragment {
    private ArrayList<account> admins = new ArrayList<>();
    private SQLiteAdapter sqlite;
    private FloatingActionButton fabAddAdmin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlite = new SQLiteAdapter(requireContext());
        UpdateAdmin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acc_admin, container, false);

        fabAddAdmin = view.findViewById(fabAdd_Admin);
        ShowAdmin(view);

        fabAddAdmin.setOnClickListener(v -> showItemDialog(-1));

        return view;
    }
    private void showItemDialog(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_add_admin, null);
        dialogBuilder.setView(dialogView);
        //chuan bi cho popup
        final EditText edtUserName = dialogView.findViewById(R.id.edtUserName);
        final EditText edtPassword = dialogView.findViewById(R.id.edtPassword);

        if(position == -1){
            dialogBuilder.setTitle("Thêm admin");
            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();

                AddNewItem(new account(userName, password, 1));
            });
        }
        else {
            dialogBuilder.setTitle("Thông tin admin");
            edtUserName.setText(admins.get(position).getUserName());
            edtPassword.setText(admins.get(position).getPassword());

            sqlite.Open();
            Cursor cursor = sqlite.SQLTable("SELECT * FROM Account WHERE userName = '" + admins.get(position).getUserName() +
                    "' AND password = '" + admins.get(position).getPassword() + "' AND aStatement = 1");
            cursor.moveToFirst();

            int adminId = cursor.getInt(0);

            dialogBuilder.setPositiveButton("Lưu", (dialog, which) -> {
                admins.get(position).setUserName(edtUserName.getText().toString());
                admins.get(position).setPassword(edtPassword.getText().toString());

                sqlite.Open();
                String updateQuery = "UPDATE Account SET userName = '" + admins.get(position).getUserName() +
                        "', password = '" + admins.get(position).getPassword() +
                        "' WHERE idAccount = " + adminId;
                sqlite.SQLRun(updateQuery);
                sqlite.Close();
            });
        }

        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void AddNewItem(account a) {
        admins.add(a);
        sqlite.Open();

        ContentValues values = new ContentValues();
        values.put("userName", a.getUserName());
        values.put("password", a.getPassword());
        values.put("aStatement", 1);

        try {
            sqlite.Insert("Account", values);
        } catch (Exception e) {
            // Xử lý lỗi
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            sqlite.Close();
        }
    }

    private void ShowAdmin(View view) {
        GridView grid = view.findViewById(R.id.grid_acc_admin);
        GridAdapterAcc adapter = new GridAdapterAcc(view.getContext(), R.id.grid_acc_admin, admins);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view1, position, id) -> {
            Toast.makeText(getContext(), admins.get(position).getUserName(), Toast.LENGTH_SHORT).show();
            showItemDialog(position);
        });
    }

    private void UpdateAdmin() {
        sqlite.Open();
        Cursor cursor = sqlite.SQLTable("SELECT userName, password FROM Account where aStatement = 1");

        while (cursor.moveToNext()) {
            try {
                String userName = cursor.getString(0);
                String password = cursor.getString(1);

                this.admins.add(new account(userName, password, 1));
            } catch (Exception e) {
                Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        cursor.close();
        sqlite.Close();
    }
}