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
        setContentView(R.layout.log); // Set the layout for this activity

        TabLayout tabLayout = findViewById(R.id.tabLayout); // Find TabLayout from XML
        ViewPager2 viewPager = findViewById(R.id.viewPager); // Find ViewPager2 from XML

        // Set up ViewPager with a FragmentStateAdapter
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                // Return different Fragments based on the tab position
                if (position == 0) {
                    return new ActivityLogExpensesFragment(); // Fragment for expenses
                } else {
                    return new ActivityLogRevenueFragment(); // Fragment for revenue
                }
            }

            @Override
            public int getItemCount() {
                return TAB_TITLES.length; // Number of tabs
            }
        });

        // Link TabLayout with ViewPager using TabLayoutMediator
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();
    }
}
