package com.bobo.locator.assist.service;

import com.bobo.locator.assist.request.UploadRequest;
import com.bobo.locator.assist.response.UploadResponse;
import com.bobo.locator.common.Logic;
import com.bobo.locator.dao.UploadDao;
import com.bobo.locator.model.DeviceData;
import com.bobo.locator.model.Message;
import com.bobo.locator.model.RouterData;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadService {
    @Autowired
    UploadDao uploadDao;

    public void process(UploadRequest request, UploadResponse response){
        RouterData routerData = Logic.getDataFromString(request.getData());
//        Message message = Logic.getMessageByUploadRequest(request);
        String time = routerData.getTime();
        List<DeviceData> deviceDataList = routerData.getData();
        uploadDao.addMessage(time, deviceDataList.get(0));
//        response.setMsg("Uploaded message: "+message.toString()+".");
        response.setMsg("Uploaded message at "+time+" .");
    }
}
