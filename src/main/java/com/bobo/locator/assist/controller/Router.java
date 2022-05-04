package com.bobo.locator.assist.controller;

import com.bobo.locator.assist.request.UploadRequest;
import com.bobo.locator.assist.response.UploadResponse;
import com.bobo.locator.assist.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class Router {
    @Autowired()
    private UploadService uploadService;

    @PostMapping("upload")
    public Object upload(@RequestBody String request){
        UploadResponse response = new UploadResponse();
        uploadService.process(request, response);
        return response;
    }

    @PostMapping("testUpload")
    public Object testUpload(@RequestBody String request){
        UploadResponse response = new UploadResponse();
        uploadService.testProcess(request, response);
        return response;
    }
}
