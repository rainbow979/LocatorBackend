package com.bobo.locator.assist.service;

import com.bobo.locator.assist.request.UploadRequest;
import com.bobo.locator.assist.response.UploadResponse;
import com.bobo.locator.common.Logic;
import com.bobo.locator.dao.UploadDao;
import com.bobo.locator.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService {
    @Autowired
    UploadDao uploadDao;

    public void process(UploadRequest request, UploadResponse response){
        Message message = Logic.getMessageByUploadRequest(request);
        uploadDao.addMessage(message);
        response.setMsg("Uploaded message: "+message.toString()+".");
    }
}
