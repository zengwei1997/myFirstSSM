package com.hubu.myFirstSSM.util;

import org.junit.Test;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;

public class TestQR {

    @Test
    public void test1(){
        String str = "https://www.qq.com/";
        String path = "d:/qrcode_qq.jpg";
        QrCodeUtil.generate(str, 300, 300, FileUtil.file(path));
        String decode_str =  QrCodeUtil.decode(FileUtil.file(path));
        System.out.println("解码后的内容:"+decode_str);
    }
}
