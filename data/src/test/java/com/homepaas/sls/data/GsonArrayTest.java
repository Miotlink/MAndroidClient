package com.homepaas.sls.data;

import com.google.gson.Gson;
import com.homepaas.sls.data.network.dataformat.request.AddEvaluationRequest;
import com.homepaas.sls.data.network.dataformat.request.DeleteMessageRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * on 2016/5/19 0019
 *
 * @author zhudongjie
 */
public class GsonArrayTest {

    private  Gson gson ;
    @Before
    public void setUp(){
        gson = new Gson();
    }

    @Test
    public void toJSon(){
        DeleteMessageRequest request = new DeleteMessageRequest("124FSR","3","6","7");
        String  json = gson.toJson(request);
        Assert.assertEquals("{\"Token\":\"124FSR\",\"Ids\":[\"3\",\"6\",\"7\"]}",json);
    }

    @Test
    public void toEvaluateJson(){

        AddEvaluationRequest request = new AddEvaluationRequest("3fe485c2914a2106d633fb5a5ded31c7","451","4.5","Fgghjjk","1",null);
        String json = gson.toJson(request);
        System.out.println(json);
    }
}
