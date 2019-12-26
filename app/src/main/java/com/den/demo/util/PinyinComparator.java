package com.den.demo.util;

import com.den.demo.model.Contact;

import java.util.Comparator;

public class PinyinComparator implements Comparator<Contact> {

    public int compare(Contact o1, Contact o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getSortTag().equals("@")
                || o2.getSortTag().equals("#")) {
            return -1;
        } else if (o1.getSortTag().equals("#")
                || o2.getSortTag().equals("@")) {
            return 1;
        } else {
            return o1.getSortTag().compareTo(o2.getSortTag());
        }
    }
}