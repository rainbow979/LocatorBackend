package com.bobo.locator.model;

public class Message {
    private String gatewayId;
    private String time;
    private String data;

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString(){
        return "id: "+ gatewayId +", time: "+time+", data: "+data;
    }
}
