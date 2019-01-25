package com.homepaas.sls.util.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;

import java.lang.reflect.Type;
import java.util.Iterator;

/**
 * Created by mhy on 2017/8/18.
 */

public class InterfaceDeserializer implements JsonDeserializer<IService> {

    // json转为对象时调用,实现JsonDeserializer<IService>接口
    @Override
    public IService deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            // if left_balls attr is not null, it is deserialized as TernaryDecisonTree class
            if (jsonObject.get("left_balls") != null) {
                ServiceSearchInfo serviceSearchInfo = new ServiceSearchInfo();
//                @SerializedName("Workers")
//                List<WorkerEntity> workerEntities;
//                @SerializedName("WholeWorkers")
//                List<WholeCityWorker> wholeCityWorkers;
//                @SerializedName("Business")
//                List<BusinessEntity> businessEntities;
//                @SerializedName("WholeBusiness")
//                List<WholeCityBusines> wholeCityBusiness;
//                @SerializedName("ServiceTypes")
//                List<NewServiceType> serviceTypes;
//
//                //接口直接序列化成实体类
//                serviceSearchInfo.heavier = context.deserialize(jsonObject.get("Workers"), TernaryDecisionTree.class);
//                serviceSearchInfo.lighter = context.deserialize(jsonObject.get("lighter"), TernaryDecisionTree.class);
//                serviceSearchInfo.equal = context.deserialize(jsonObject.get("equal"), TernaryDecisionTree.class);
//                return bt;
            } else {
                // if counterfeit_ball attr is not null, it is deserialized as Leaf class
//                if (jsonObject.get("counterfeit_ball")!=null) {
//                    int counterfeit_ball = jsonObject.get("counterfeit_ball").getAsInt();
//                    String weight = jsonObject.get("weight").getAsString();
//                    Leaf lf = new Leaf(counterfeit_ball, weight);
//                    return lf;
//                }
            }

        }

        return null;
    }



    private int[] jsonArrayToIntArray(JsonElement je) {
        JsonArray ja = je.getAsJsonArray();
        Iterator<JsonElement> iterator = ja.iterator();
        int[] balls = new int[ja.size()];
        for (int i =0; i < ja.size(); i++) {
            balls[i] = iterator.next().getAsInt();
        }
        return balls;
    }
}
