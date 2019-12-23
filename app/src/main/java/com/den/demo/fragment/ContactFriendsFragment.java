package com.den.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.den.demo.R;
import com.den.demo.component.SideBar;

public class ContactFriendsFragment extends Fragment {

    private SideBar sideBar;

    private TextView alphabetText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_friends_frament, container, false);
        sideBar = (SideBar) view.findViewById(R.id.friendSideBar);
        alphabetText = (TextView) view.findViewById(R.id.alphabetViewDialog);
        sideBar.setTextViewDialog(alphabetText);
        return view;
    }

}
