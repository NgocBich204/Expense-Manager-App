package com.example.smartwall;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView; // Thanh điều hướng
    private FrameLayout frameLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Log để kiểm tra quá trình khởi tạo
        Log.d("Homepage", "onCreate: Khởi tạo activity");

        // Khởi tạo và thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Expense Manager");
        setSupportActionBar(toolbar);

        // Khởi tạo DrawerLayout và ActionBarDrawerToggle để điều khiển ngăn kéo
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Khởi tạo NavigationView và thiết lập trình nghe sự kiện
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        // Khởi tạo BottomNavigationView và thiết lập trình nghe sự kiện
        bottomNavigationView = findViewById(R.id.BottomNavigationbar);
        frameLayout = findViewById(R.id.main_frame);

        // Log để kiểm tra bottomNavigationView
        if (bottomNavigationView == null) {
            Log.e("Homepage", "onCreate: BottomNavigationView is null");
        } else {
            Log.d("Homepage", "onCreate: BottomNavigationView initialized");
            bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.Home) {
                        bottomNavigationView.setItemBackgroundResource(R.color.home_color);
                    } else if (itemId == R.id.detail) {
                        bottomNavigationView.setItemBackgroundResource(R.color.home_color);
                    } else if (itemId == R.id.calender) {
                        bottomNavigationView.setItemBackgroundResource(R.color.home_color);
                    } else if (itemId == R.id.setting) {
                        bottomNavigationView.setItemBackgroundResource(R.color.home_color);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Đóng ngăn kéo nếu nó đang mở
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Gọi phương thức mặc định nếu ngăn kéo không mở
            super.onBackPressed();
        }
    }

    // Phương thức chuyển đổi giữa các Fragment khi một mục trong NavigationView được chọn
    public void displaySelectedListener(int itemId) {
        Fragment fragment = null;
        if (itemId == R.id.Home) {
            // Handle Home click
            fragment = new Home_Fragment();
        } else if (itemId == R.id.Detail) {
            // Handle Detail click
            fragment = new Detail_Fragment();
        } else if (itemId == R.id.Calender) {
            // Handle Calender click
            fragment = new Calender_Fragment();
        } else if (itemId == R.id.Setting) {
            // Handle Setting click
            fragment = new Setting_Fragment();
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START); // Đóng ngăn kéo sau khi chọn
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Gọi phương thức displaySelectedListener với ID của mục được chọn
        displaySelectedListener(menuItem.getItemId());
        return true;
    }
}
