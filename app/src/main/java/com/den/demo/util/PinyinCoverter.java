package com.den.demo.util;

import com.github.promeg.pinyinhelper.Pinyin;

public class PinyinCoverter {

    public static String InitialsCovertToChar(String initials) {
        if(initials.length() < 1) {
            return null;
        }
        // 截取第一个字符
        initials = initials.substring(0,1);
        if(initials.matches("[A-Z]") || initials.matches("[a-z]")) {
            return initials.toUpperCase();
        }
        //判读是否为汉字
        if(Pinyin.isChinese(initials.charAt(0))) {
            return Pinyin.toPinyin(initials.charAt(0)).substring(0,1);
        }
        //特殊字符返回
        return "#";
    }

}
