package com.den.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.den.demo.R;
import com.den.demo.activity.ChatWindowActivity;
import com.den.demo.adapter.MessageDataAdapter;
import com.den.demo.model.MessageItem;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    // listView
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        listView = (ListView) view.findViewById(R.id.messageListView);

        List<MessageItem> messages = new ArrayList<>();
        messages.add(new MessageItem("许家印", "hello"));
        messages.add(new MessageItem("马化腾", "hello"));
        messages.add(new MessageItem("马云家", "hello"));
        messages.add(new MessageItem("杨惠妍", "hello"));
        messages.add(new MessageItem("王健林", "hello"));
        messages.add(new MessageItem("王卫", "hello"));
        messages.add(new MessageItem("李彦宏", "hello"));
        messages.add(new MessageItem("何享健", "hello"));
        messages.add(new MessageItem("严昊", "hello"));
        messages.add(new MessageItem("丁磊", "hello"));
        messages.add(new MessageItem("李书福", "hello"));
        messages.add(new MessageItem("张志东", "hello"));
        messages.add(new MessageItem("宗庆后", "hello"));
        messages.add(new MessageItem("姚振华", "hello"));
        messages.add(new MessageItem("张近东", "hello"));
        messages.add(new MessageItem("卢志强", "hello"));
        messages.add(new MessageItem("王文银", "hello"));
        messages.add(new MessageItem("严彬", "hello"));
        messages.add(new MessageItem("孙宏斌", "hello"));
        messages.add(new MessageItem("周群飞", "hello"));
        messages.add(new MessageItem("刘强东", "hello"));
        messages.add(new MessageItem("雷军", "这条消息很长很长 ----------------------------------------- 到这里------------------------------后面还有"));

        MessageDataAdapter messageDataAdapter = new MessageDataAdapter(view.getContext(), messages);

        listView.setAdapter(messageDataAdapter);


        //ListView item的点击事件，跳转到聊天窗口
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Intent intent = new Intent(inflater.getContext(), ChatWindowActivity.class);
            startActivity(intent);
        });
        return view;
    }

}
