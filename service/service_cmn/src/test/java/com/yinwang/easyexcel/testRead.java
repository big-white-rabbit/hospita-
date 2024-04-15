package com.yinwang.easyexcel;

import com.alibaba.excel.EasyExcel;

public class testRead {
    public static void main(String[] args) {
        String path = "E:\\excel\\data.xlsx";
        EasyExcel.read(path, UserData.class, new ExcelListener()).sheet().doRead();
    }
}
