package com.example.xgreen.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.xgreen.Fragment.DatHangFragment;
import com.example.xgreen.Fragment.HoatDongFragment;
import com.example.xgreen.Fragment.KhacFragment;
import com.example.xgreen.Fragment.TrangChuFragment;
import com.example.xgreen.Fragment.SellFragment;
import com.example.xgreen.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavMenu;
    private ViewPager2 viewPager;
    private FragmentStateAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Chỉnh màu status bar */
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.drawable.statusbar_gradient);
        /* Chỉnh màu status bar */
        setContentView(R.layout.activity_main);
        /* Ánh xạ view */
        bottomNavMenu = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        setUpViewPage();

        /*Sự kiện Menu bottom Nav*/
        bottomNavMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionHome:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.actionOrder:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.actionSell:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.actionHistory:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.actionOther:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return false;

            }
        });
        int fragmentIndex = getIntent().getIntExtra("fragmentIndex", 0);
        viewPager.setCurrentItem(fragmentIndex);
        /*Kết thúc sự kiện Menu bottom Nav*/

    }

    private void setUpViewPage() {
        fragmentAdapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new TrangChuFragment();
                    case 1:
                        return new DatHangFragment();
                    case 2:
                        return new SellFragment();
                    case 3:
                        return new HoatDongFragment();
                    case 4:
                        return new KhacFragment();
                    default:
                        return new TrangChuFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        };
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setUserInputEnabled(false);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavMenu.getMenu().findItem(R.id.actionHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavMenu.getMenu().findItem(R.id.actionOrder).setChecked(true);
                        break;
                    case 2:
                        bottomNavMenu.getMenu().findItem(R.id.actionSell).setChecked(true);
                        break;
                    case 3:
                        bottomNavMenu.getMenu().findItem(R.id.actionHistory).setChecked(true);
                        break;
                    case 4:
                        bottomNavMenu.getMenu().findItem(R.id.actionOther).setChecked(true);
                        break;
                }
            }
        });
    }
}
