package com.den.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.den.demo.R;
import com.den.demo.adapter.HomePageActivityViewPagerAdapter;
import com.den.demo.fragment.CommunityFragment;
import com.den.demo.fragment.ContactFragment;
import com.den.demo.fragment.MessageFragment;
import com.den.demo.fragment.PersonalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.lang.reflect.Field;

import q.rorbin.badgeview.QBadgeView;


public class HomePageActivity extends AppCompatActivity {

    private ViewPager mainActivityViewPager;
    //底部导航
    private BottomNavigationView bottomNavView;
    // viewPager适配器
    private HomePageActivityViewPagerAdapter adapter;
    // 联系人消息计数
    private TextView contactMessageCountTextView;
    // 消息计数
    private TextView messageCountTextView;
    // 社区消息计数
    private TextView communityMessageCountTextView;
    // 我的消息计数
    private TextView personMessageCountTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mainActivityViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.main_bottom_nav_view);
        adapter = new HomePageActivityViewPagerAdapter(getSupportFragmentManager());
        // 设置红点
        // 这里我设置参数为1
        for (int i = 0; i < bottomNavView.getChildCount(); i++) {
            showBadgeView(i, bottomNavView);
        }

        //        为Adapter添加Fragment
        adapter.addFragment(new ContactFragment());
        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new CommunityFragment());
        adapter.addFragment(new PersonalFragment());

        mainActivityViewPager.setAdapter(adapter);

//        为 BottomNavigationView 的菜单项  设置监听事件
        bottomNavView.setOnNavigationItemSelectedListener(item -> {
//               获取到菜单项的Id
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.tab_contact:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;
                case R.id.tab_message:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;

                case R.id.tab_community:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;

                case R.id.tab_person:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;

            }
            // true 会显示这个Item被选中的效果 false 则不会
            return true;
        });
//        为 ViewPager 设置监听事件
        mainActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当 ViewPager 滑动后设置BottomNavigationView 选中相应选项
                bottomNavView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    /**
//     * 顶部导航
//     * @param menu
//     * @return
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
//        return true;
//    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param bottomNavView 显示的数字，小于等于0是将不显示
     */
    private void showBadgeView(int viewIndex, BottomNavigationView bottomNavView) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavView.getChildAt(viewIndex);

        for (int i = 0; i < menuView.getChildCount(); i ++) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(i);

            BottomNavigationItemView itemView = (BottomNavigationItemView) view;

            //加载我们的角标View，新创建的一个布局
            View badge = LayoutInflater.from(this).inflate(R.layout.badge_layout, menuView, false);
            //添加到Tab上
            itemView.addView(badge);
            TextView textView = badge.findViewById(R.id.badgeTextView);
            switch (i) {
                case 0:
                   contactMessageCountTextView = textView;
                   contactMessageCountTextView.setText(String.valueOf(i));
                case 1:
                    messageCountTextView = textView;
                    messageCountTextView.setText(String.valueOf(i));
                case 2:
                    communityMessageCountTextView = textView;
                    communityMessageCountTextView.setText(String.valueOf(i));
                case 3:
                    personMessageCountTextView = textView;
                    personMessageCountTextView.setText(String.valueOf(i));
            }
        }
    }
}
