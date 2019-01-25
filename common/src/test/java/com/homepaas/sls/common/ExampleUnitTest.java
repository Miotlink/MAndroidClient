package com.homepaas.sls.common;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void toPinyin(){
        String string = "如果需要5创建类型里面可能a需要用d到的对象的话";
        String[] strings = PinyinUtils.characterToPinyin(string);
        System.out.println(Arrays.toString(strings));
    }

    @Test
    public void testPinyin(){
        String install = "家电安装";

        String sInstall = PinyinUtils.toPinyinPhrase(install).toLowerCase();
        boolean contains = sInstall.contains(PinyinUtils.toPinyinPhrase("安").toLowerCase());
        assertEquals(true,contains);

        boolean b = install.contains("安");

        assertTrue(b);
    }
}