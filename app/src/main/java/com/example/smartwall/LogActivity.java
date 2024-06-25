package com.example.smartwall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LogActivity extends AppCompatActivity {
    private static final String[] TAB_TITLES = new String[]{"Lịch sử thu", "Lịch sử chi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return ListFragment.newInstance(position == 0 ? "thu" : "chi");
            }

            @Override
            public int getItemCount() {
                return TAB_TITLES.length;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();
    }
}
