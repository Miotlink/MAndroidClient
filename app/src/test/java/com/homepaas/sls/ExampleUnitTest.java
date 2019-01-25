package com.homepaas.sls;

import android.support.v4.util.ArrayMap;

import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NotNormalDeviceException;
import com.homepaas.sls.entity.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private static final String TAG = "ExampleUnitTest";
    private static final boolean DEBUG = BuildConfig.DEBUG;


    @Test
    public void addition_isCorrect() throws Exception {

//        027-88776654
//        COO-彪哥<uwb@w.cn> 2017/9/1 17:39:04
//        027888667788
//        COO-彪哥<uwb@w.cn> 2017/9/1 17:39:15
//        4008887665
//        COO-彪哥<uwb@w.cn> 2017/9/1 17:39:50
//        13399887766
//        String text = "工人梅琴测试234234234";
//        Pattern pattern = Pattern.compile("(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[0589]\\d{7}");
//        Matcher matcher = pattern.matcher(text);
//        StringBuffer bf = new StringBuffer(64);
//        while (matcher.find()) {
//            bf.append(matcher.group()).append(",");
//        }
//        int len = bf.length();
//        if (len > 0) {
//            bf.deleteCharAt(len - 1);
//        }
//
//        System.out.println(bf.toString());
//        String regEx="[^0-9]{11}";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher("你好啊13399887766什么鬼啊12312");
//        String num=m.replaceAll("").trim().toString();
//        if(num.length()==20)
//        {
//            System.out.println("true");
//        }
//        else
//        System.out.println(""+num);


        System.out.println(-1>-2);
    }


    @Test
    public void itarNullable() {
        List<String> list = null;
        for (String a : list) {

        }
    }

    @Test
    public void flagCompute() {
        int a = 1;
        int b = 1 << 1;
        assertEquals(a | b, 3);

        assertEquals(3 & ~b, a);
        assertEquals(1 & ~b, a);
    }

    @Test
    public void mapPut() {
        Map<String, String> map = new ArrayMap<>();
        map.put("1", "A");
        map.put("2", "B");
        map.put("3", "C");
        map.put("2", "F");

        assertEquals(3, map.size());
        assertEquals("F", map.get("2"));
    }

    @Test
    public void regexTest() {
        Pattern pattern = Pattern.compile("(((\\w+\\.)*(\\w+Exception)\\s*:)+\\s*)*(.+)");
        Matcher matcher = pattern.matcher("com.homepaas.sls.AuthException: com.homepaas.sls.AuthException: 验证错误");
        boolean b = matcher.matches();
        assertEquals(true, b);
        String result = matcher.group(5);
        assertEquals("验证错误", result);

//        Matcher matcher1 = pattern.matcher("错误信息");
//        boolean b1 = matcher1.matches();
//        assertEquals(false,b1);

    }

    @Test
    public void ExceptionTest() {
        try {
            try {
                throw new NotNormalDeviceException("错误谁被");
            } catch (NotNormalDeviceException e) {
                throw new GetDataException(e);
            }
        } catch (GetDataException e) {
            assertEquals("错误谁被", e.getMessage());
        }

    }

    @Test
    public void listDeleteTest() {
        User user = new User();
        user.name = "张三";
        user.id = 1;

        List<User> list = new ArrayList<>();
        list.add(user);

        User user1 = new User();
        user1.id = 1;
        list.remove(user1);
    }


    @Test
    public void listContain() {

    }
}