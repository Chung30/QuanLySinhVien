package vn.itplus.doanqlsinhvien.View.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.itplus.doanqlsinhvien.R;

public class ViewPageAccount extends AppCompatActivity {
    private TabLayout tabLayoutAcc;
    private ViewPager2 viewPagerAcc;
    private ViewPageAccAdapter viewPageAccAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_account);

        tabLayoutAcc = findViewById(R.id.tab_layout_acc);
        viewPagerAcc = findViewById(R.id.view_pager_acc);

        viewPageAccAdapter = new ViewPageAccAdapter(this);
        viewPagerAcc.setAdapter(viewPageAccAdapter);

        new TabLayoutMediator(tabLayoutAcc, viewPagerAcc, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Admin");
                    break;
                case 1:
                    tab.setText("Teacher");
                    break;
                case 2:
                    tab.setText("Student");
                    break;
            }
        }).attach();
    }
}