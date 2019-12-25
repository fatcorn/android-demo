package com.den.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.den.demo.R;
import com.den.demo.model.Contact;

import java.util.List;

public class ContactsDataAdapter extends BaseAdapter {

    private List<Contact> list = null;

    private Context mContext;


    public ContactsDataAdapter(Context mContext, List<Contact> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
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
        final Contact mContent = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.friend_list_item_layout, null);
            viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.friendAvatarImageView);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.friendNickNameTextView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(this.list.get(position).getNickName());

        return convertView;
    }
}

final class ViewHolder {
    ImageView ivAvatar;
    TextView tv;
}
