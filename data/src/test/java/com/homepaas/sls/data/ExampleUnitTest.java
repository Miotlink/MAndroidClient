package com.homepaas.sls.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.homepaas.sls.data.entity.User;
import com.homepaas.sls.domain.exception.GetDataException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void gsonTest() {
        String gsonString = "{\n" +
                "    \"meta\": {\n" +
                "        \"errorCode\": \"0\",\n" +
                "        \"errorMsg\": \"no error\"\n" +
                "    },\n" +
                "    \"data\": {\n" +
                "        \"services\": [\n" +
                "            {\n" +
                "                \"type_id\": \"1\",\n" +
                "                \"type_logo\": \"123\",\n" +
                "                \"type_name\": \"12345\",\n" +
                "                \"service_id\": \"1234567\",\n" +
                "                \"service_name\": \"123456789\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"type_id\": \"0\",\n" +
                "                \"type_logo\": \"321\",\n" +
                "                \"type_name\": \"54321\",\n" +
                "                \"service_id\": \"7654321\",\n" +
                "                \"service_name\": \"987654321\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.create();
//        Type type = new TypeToken<ResponseWrapper<DataSegmentWrapper_1<List<LifeServiceEntity>>>>(){}.getType();
//        ResponseWrapper<DataSegmentWrapper_1<List<LifeServiceEntity>>> responseWrapper = gson.fromJson(gsonString, type);
//        System.out.print(responseWrapper.meta.getErrorMsg());
    }

    @Test
    public void jsonString() {

        int workerId = 12;

        String token = "hsaifiorh8932hrasbj";

        JsonObject jsonObject = new JsonObject();

        // JSONObject object = new JSONObject();
//        try {
        jsonObject.addProperty("WorkerId", workerId);
        jsonObject.addProperty("Token", token);
//            jsonObject.put("WorkerId",workerId);
//            jsonObject.put("Token",token);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        String request = jsonObject.toString();
        System.out.print(request);
    }

    @Test
    public void jsonArray() {
        String[] strings = {"ac", "de", "ef"};
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "张三");
        JsonArray array = new JsonArray();
        for (String s : strings) {
            JsonElement jsonElement = new JsonPrimitive(s);
            array.add(jsonElement);
        }
        jsonObject.add("list", array);
        System.out.print(jsonObject);
    }

    @Test
    public void exceptionMessage() {
        try {
            throw new GetDataException("获取失败");
        } catch (GetDataException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void displace() {
        int a = 2;
        int b = 1 << 1;
        assertEquals(a, b);
    }


    @Test
    public void testGson() {
        //GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();//gsonBuilder.create();
        User user = new User();
        user.name = "zhudongjie";
        user.age = 14;
        user.phones = new String[]{"121223", "123233"};

        String json = gson.toJson(user);
        System.out.println(json);
    }
}