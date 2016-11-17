package com.gearcode.gearpress.test;

/**
 * Created by jason on 16/11/17.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("a\r\nb\nc".replaceAll("[\\r]?[\\n]", "<br />"));
    }
}
