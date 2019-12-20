package com.den.demo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.den.demo.R;
import com.den.demo.adapter.HomePageActivityViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePageActivity extends AppCompatActivity {

    private ViewPager mainActivityViewPager;
    private BottomNavigationView bottomNavView;
    private HomePageActivityViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mainActivityViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.main_bottom_nav_view);

        adapter = new HomePageActivityViewPagerAdapter(getSupportFragmentManager());
//        为Adapter添加Fragment
        adapter.addFragment(new ContactFragment());
        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new CommunityFragment());
        adapter.addFragment(new PersonalFragment());
//        为 BottomNavigationView 的菜单项  设置监听事件
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//               获取到菜单项的Id
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.tab_contact:
                        mainActivityViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_message:
                        mainActivityViewPager.setCurrentItem(1);
                        break;

                    case R.id.tab_community:
                        mainActivityViewPager.setCurrentItem(2);
                        break;

                    case R.id.tab_person:
                        mainActivityViewPager.setCurrentItem(3);
                        break;

                }
                // true 会显示这个Item被选中的效果 false 则不会
                return true;
            }
        });
//        为 ViewPager 设置监听事件
        mainActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
