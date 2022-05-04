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

    public void process(String request, UploadResponse response){
        RouterData routerData = Logic.getDataFromString(request);
        List<DeviceData> deviceDataList = routerData.getData();
        uploadDao.addMessage(routerData.getId(), routerData.getTime(), deviceDataList.get(0));
        response.setMsg("Uploaded message at "+routerData.time+" .");
    }

    public void testProcess(String request, UploadResponse response){
        uploadDao.testAdd();
        response.setMsg("Test upload success.");
    }
}
