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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UploadService {
    @Autowired
    UploadDao uploadDao;

    public void process(String request, UploadResponse response){
        request = URLDecoder.decode(request, StandardCharsets.UTF_8);
        RouterData routerData = Logic.getDataFromString(request);
        List<DeviceData> deviceDataList = routerData.getData();
        DeviceData myPhone = deviceDataList.get(0);
        System.out.println(myPhone.getMac());
        String myMac = "c4:e1:a1:bf:cb:b3";
        String[] probeMac=new String[]
                {"14:6B:9C:F4:04:3E", "14:6B:9C:F4:04:39", "14:6B:9C:F4:03:F1"};
        for(DeviceData d: deviceDataList){
            if(d.getMac().equals(myMac))
                myPhone = d;
        }
        uploadDao.addMessage(routerData.getId(), routerData.getTime(), routerData.getMmac(), myPhone);
        response.setMsg("Uploaded message at "+routerData.time+" .");
    }

    public void testProcess(String request, UploadResponse response){
//        System.out.println(new String(request.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_8));
//        System.out.println(request);
        String decoded = URLDecoder.decode(request, StandardCharsets.UTF_8);
        System.out.println(decoded);
        response.setMsg("Test upload success.");
    }
}
