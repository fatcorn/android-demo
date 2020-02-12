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
import com.den.demo.util.PinyinComparator;
import com.den.demo.util.PinyinCoverter;

import java.util.Collections;
import java.util.List;

/**
 * 联系人页面数据适配
 */
public class ContactsDataAdapter extends BaseAdapter {

    // 联系人列表
    private List<Contact> list = null;

    private Context mContext;

    // 分组标记
    private String groupTag;


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
            viewHolder.ivAvatar =  convertView.findViewById(R.id.friendAvatarImageView);
            viewHolder.tv = convertView.findViewById(R.id.friendNickNameTextView);
            viewHolder.groupTagTextView = convertView.findViewById(R.id.friendGroupTag);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            viewHolder.groupTagTextView.setVisibility(View.VISIBLE);
            viewHolder.groupTagTextView.setText(mContent.getSortTag());
        }else{
            viewHolder.groupTagTextView.setVisibility(View.GONE);
        }

        System.out.println(groupTag);
        viewHolder.tv.setText(this.list.get(position).getNickName());

        return convertView;
    }

    static final class ViewHolder {
        //联系人分组标签
        TextView groupTagTextView;
        //头像
        ImageView ivAvatar;
        //联系人名称
        TextView tv;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortTag().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortTag();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}


