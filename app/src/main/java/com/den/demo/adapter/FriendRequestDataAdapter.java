package com.den.demo.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.den.demo.R;
import com.den.demo.model.Message;
import com.den.demo.net.http.NetServiceHandler;
import com.den.demo.net.http.entity.FriendRequest;
import com.den.demo.net.http.entity.ResponseMessage;
import com.den.demo.net.http.enums.CommonEnum;
import com.den.demo.net.http.enums.FriendRequestStatusEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 聊天窗口数据适配器
 */
public class FriendRequestDataAdapter extends BaseAdapter {

    // 好友请求列表
    private List<FriendRequest> friendRequestList = null;

    private Context mContext;

    @Getter
    // ui刷新助手
    private Handler handler = null;

    // 分组标记
    private String groupTag;
    //viewHolder
    private ViewHolder viewHolder = null;


    public FriendRequestDataAdapter(Context mContext, List<FriendRequest> messagesList) {
        this.mContext = mContext;
        this.friendRequestList = messagesList;
        this.handler = new Handler(msg -> {
            addItem();
            return true;
        });
    }

    // 添加测试
    public void addItem() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return friendRequestList.size();
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

        final FriendRequest friendRequest = friendRequestList.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout_friends_request, null);
            viewHolder.ivAvatar =  convertView.findViewById(R.id.lilfr_friendAvatar);
            viewHolder.userNameTv =  convertView.findViewById(R.id.lilfr_friendNickName);
            viewHolder.agreeBtn = convertView.findViewById(R.id.lilfr_agreeBtn);
            viewHolder.agreedTv = convertView.findViewById(R.id.lilfr_agreeTv);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 用户名
        viewHolder.userNameTv.setText(String.valueOf(friendRequest.getRequestId()));

        if(friendRequest.getStatus().equals(FriendRequestStatusEnum.VERIFYING)) {
            viewHolder.agreeBtn.setOnClickListener(x -> {
                //点击后修改界面，本地数据未变
                x.setVisibility(View.INVISIBLE);
                viewHolder.agreedTv.setVisibility(View.VISIBLE);
                viewHolder.agreedTv.setText(FriendRequestStatusEnum.ACCEPTED.getCnName());

                //发送同意操作
                new Thread(() -> {
                    Call<ResponseMessage> call =  NetServiceHandler.handler().handFriendRequest(friendRequest.getId(), CommonEnum.ENABLE);
                    Response<ResponseMessage> response = null;
                    try {
                        response = call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(response == null ||  response.body() == null) {
                        System.out.println("请求失败");
                        return;
                    }
                    if(response.body().getCode() == 0) {
                        System.out.println("请求成功");
                    }
                }).start();
            });
        } else {
            viewHolder.agreeBtn.setVisibility(View.INVISIBLE);
            viewHolder.agreedTv.setVisibility(View.VISIBLE);
            viewHolder.agreedTv.setText(friendRequest.getStatus().getCnName());
        }

        return convertView;
    }

    // listView item
    static final class ViewHolder {
        //头像
        ImageView ivAvatar;
        //用户名
        TextView userNameTv;
        // 用户同意操作
        Button agreeBtn;
        // 用户已操作提示
        TextView agreedTv;
    }


}


