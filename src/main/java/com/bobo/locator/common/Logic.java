package com.bobo.locator.common;

import com.bobo.locator.assist.request.UploadRequest;
import com.bobo.locator.model.RouterData;
import com.bobo.locator.model.Message;
import com.google.gson.*;

public class Logic {
//    public static Message getMessageByUploadRequest(UploadRequest request){
//        Message message = new Message();
//        message.setGatewayId(request.getGatewayId());
//        message.setTime(request.getTime());
//        message.setData(request.getData());
//        return message;
//    }

    public static RouterData getDataFromString(String s){
        Gson gson = new Gson();
        String prefix = "data=";
        return gson.fromJson(s.substring(prefix.length()), RouterData.class);
    }
}
