package com.den.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.den.demo.LoginActivity;
import com.den.demo.R;
import com.den.demo.activity.FriendSearch;
import com.den.demo.activity.HomePageActivity;
import com.den.demo.adapter.ContactsDataAdapter;
import com.den.demo.component.SideBar;
import com.den.demo.model.Contact;
import com.den.demo.util.PinyinComparator;
import com.den.demo.util.PinyinCoverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactFriendsFragment extends Fragment {

    // 侧边栏
    private SideBar sideBar;
    // 选中字母表
    private TextView alphabetText;
    // listView
    private ListView listView;
    // 联系人列表
    private List<Contact> contacts;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_contact_friends, container, false);
        sideBar = (SideBar) view.findViewById(R.id.friendSideBar);
        alphabetText = (TextView) view.findViewById(R.id.alphabetViewDialog);
        sideBar.setTextViewDialog(alphabetText);
        listView = view.findViewById(R.id.friendsListView);


        contacts = new ArrayList<>();
        contacts.add(new Contact("许家印"));
        contacts.add(new Contact("马化腾"));
        contacts.add(new Contact("马云家"));
        contacts.add(new Contact("杨惠妍"));
        contacts.add(new Contact("王健林"));
        contacts.add(new Contact("王卫"));
        contacts.add(new Contact("李彦宏"));
        contacts.add(new Contact("何享健"));
        contacts.add(new Contact("严昊"));
        contacts.add(new Contact("丁磊"));
        contacts.add(new Contact("李书福"));
        contacts.add(new Contact("张志东"));
        contacts.add(new Contact("宗庆后"));
        contacts.add(new Contact("姚振华"));
        contacts.add(new Contact("张近东"));
        contacts.add(new Contact("卢志强"));
        contacts.add(new Contact("王文银"));
        contacts.add(new Contact("严彬"));
        contacts.add(new Contact("孙宏斌"));
        contacts.add(new Contact("周群飞"));
        contacts.add(new Contact("刘强东"));
        contacts.add(new Contact("雷军"));
        contacts.add(new Contact(("Jesse")));

        // 根据名称，设置排序标签
        contacts.forEach(x -> {
            String sortTag = PinyinCoverter.InitialsCovertToChar(x.getNickName());

            x.setSortTag(sortTag);
        });

        Collections.sort(contacts, new PinyinComparator());

        listView.setAdapter(new ContactsDataAdapter(view.getContext(), contacts));

        return view;
    }

    // 加载菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_friends,menu);
    }

    // 菜单点击操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //添加好友,跳转到添加好友页面
            case R.id.action_add_friends:
                // 跳转
                Intent intent=new Intent(getContext(), FriendSearch.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
