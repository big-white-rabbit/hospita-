package com.yinwang.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class testWrite {
    public static void main(String[] args) {
        String path = "E:\\excel\\data.xlsx";
        List<UserData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData userData = new UserData();
            userData.setUid(i);
            userData.setUserName("lucy" + i);
            list.add(userData);
        }
        EasyExcel.write(path, UserData.class).sheet("用户信息").doWrite(list);
    }
}
