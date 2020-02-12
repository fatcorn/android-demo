package com.den.demo.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.den.demo.R;
import com.den.demo.model.MessageItem;

import java.util.List;

import lombok.Getter;

/**
 * 消息页面数据适配器
 */
public class MessageDataAdapter extends BaseAdapter {

    // 联系人列表
    private List<MessageItem> messageList = null;

    private Context mContext;

    @Getter
    // ui刷新助手
    private Handler handler = null;

    // 分组标记
    private String groupTag;


    public MessageDataAdapter(Context mContext, List<MessageItem> list) {
        this.mContext = mContext;
        this.messageList = list;
        this.handler = new Handler(msg -> {
            addItem();
            return true;
        });
    }

    // 添加测试
    public void addItem() {
        final MessageItem message = new MessageItem();
        message.setNickName("添加测试");
        message.setLastMessage("AABBCCDDEEFF");
        messageList.add(message);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final MessageItem mContent = messageList.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_list_item_layout, null);
            viewHolder.ivAvatar =  convertView.findViewById(R.id.messageAvatarImageView);
            viewHolder.nickNameTextView = convertView.findViewById(R.id.messageNickNameTextView);
            viewHolder.lastMessageTextView = convertView.findViewById(R.id.messageLastMessageTextView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        System.out.println(groupTag);
        viewHolder.nickNameTextView.setText(mContent.getNickName());
        viewHolder.lastMessageTextView.setText(mContent.getLastMessage());

        return convertView;
    }

    // listView item
    static final class ViewHolder {
        //头像
        ImageView ivAvatar;
        // 联系人昵称
        TextView nickNameTextView;
        //最新消息
        TextView lastMessageTextView;
    }


}


