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
import com.den.demo.model.Message;

import java.util.List;
import java.util.Random;

import lombok.Getter;

/**
 * 聊天窗口数据适配器
 */
public class ChatWindowDataAdapter extends BaseAdapter {

    // 联系人列表
    private List<Message> messageList = null;

    private Context mContext;

    @Getter
    // ui刷新助手
    private Handler handler = null;

    // 分组标记
    private String groupTag;


    public ChatWindowDataAdapter(Context mContext, List<Message> messagesList) {
        this.mContext = mContext;
        this.messageList = messagesList;
        this.handler = new Handler(msg -> {
            addItem();
            return true;
        });
    }

    // 添加测试
    public void addItem() {
        final Message message = new Message();
        message.setMessage("添加测试");
        message.setReceiveTime(System.nanoTime());
        message.setTextByMe(new Random().nextBoolean());
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
        final Message message = messageList.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout_chat_window, null);
            viewHolder.leftIvAvatar =  convertView.findViewById(R.id.chatWindowLeftAvatar);
            viewHolder.rightIvAvatar =  convertView.findViewById(R.id.chatWindowRightAvatar);
            viewHolder.messageLeftTextView = convertView.findViewById(R.id.chatWindowLeftText);
            viewHolder.messageRightTextView = convertView.findViewById(R.id.chatWindowRightText);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(message.isTextByMe()) {
            viewHolder.messageRightTextView.setText(message.getMessage());
        } else {
            viewHolder.messageLeftTextView.setText(message.getMessage());
        }
        return convertView;
    }

    // listView item
    static final class ViewHolder {
        //头像
        ImageView leftIvAvatar;
        //头像
        ImageView rightIvAvatar;
        //左侧消息
        TextView messageLeftTextView;
        //右侧消息
        TextView messageRightTextView;
    }


}


