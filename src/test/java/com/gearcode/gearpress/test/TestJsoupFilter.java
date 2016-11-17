package com.gearcode.gearpress.test;

import org.jsoup.Jsoup;

/**
 * Created by jason on 16/11/11.
 */
public class TestJsoupFilter {
    public static void main(String[] args) {
        String text = Jsoup.parse("哈哈哈\n呵呵呵\r\n嘿嘿嘿").html();
        System.out.println(text);
    }
}
