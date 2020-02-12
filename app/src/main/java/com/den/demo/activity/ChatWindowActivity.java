package com.den.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.den.demo.R;
import com.den.demo.adapter.ChatWindowDataAdapter;
import com.den.demo.model.Message;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatWindowActivity extends AppCompatActivity {

    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = findViewById(R.id.chatWindowListView);

        List<Message> messages = new ArrayList<>();
        Random random = new Random();

        messages.add(new Message("聊天历史", 0L,random.nextBoolean(),System.nanoTime()));
        messages.add(new Message("聊天历史", 0L,random.nextBoolean(),System.nanoTime()));

        ChatWindowDataAdapter chatWindowDataAdapter = new ChatWindowDataAdapter(this.getBaseContext(), messages);

        listView.setAdapter(chatWindowDataAdapter);

        Thread thread = new Thread(() -> {
            while (true) {
                android.os.Message message = new android.os.Message();

                message.what = 1;
                chatWindowDataAdapter.getHandler().sendMessage(message);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }
}
