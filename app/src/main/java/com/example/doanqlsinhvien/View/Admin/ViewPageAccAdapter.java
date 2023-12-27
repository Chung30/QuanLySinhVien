package com.example.doanqlsinhvien.View.Admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doanqlsinhvien.View.Admin.FragmentAcc.AdminFragment;
import com.example.doanqlsinhvien.View.Admin.FragmentAcc.StudentFragment;
import com.example.doanqlsinhvien.View.Admin.FragmentAcc.TeacherFragment;

public class ViewPageAccAdapter extends FragmentStateAdapter {
    public ViewPageAccAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AdminFragment();
            case 1:
                return new TeacherFragment();
            case 2:
                return new StudentFragment();
            default:
                return new AdminFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
