package com.den.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.den.demo.R;
import com.den.demo.adapter.ContactsDataAdapter;
import com.den.demo.component.SideBar;
import com.den.demo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFriendsFragment extends Fragment {

    // 侧边栏
    private SideBar sideBar;
    // 选中字母表
    private TextView alphabetText;

    private ListView listView;

    private List<Contact> contacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_friends_frament, container, false);
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

        listView.setAdapter(new ContactsDataAdapter(view.getContext(), contacts));

        return view;
    }

}
