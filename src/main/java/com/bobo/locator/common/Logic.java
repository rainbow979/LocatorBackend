package com.bobo.locator.common;

import com.bobo.locator.assist.request.UploadRequest;
import com.bobo.locator.model.Message;

public class Logic {
    public static Message getMessageByUploadRequest(UploadRequest request){
        Message message = new Message();
        message.setGatewayId(request.getGatewayId());
        message.setTime(request.getTime());
        message.setData(request.getData());
        return message;
    }
}
