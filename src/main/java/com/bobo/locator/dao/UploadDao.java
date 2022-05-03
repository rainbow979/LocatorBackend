package com.bobo.locator.dao;

import com.bobo.locator.model.DeviceData;
import com.bobo.locator.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UploadDao {
    @Insert("insert into message " +
            "(id, time, mac, rssi, range, ts, tmc) values " +
            "(#{id, jdbcType=VARCHAR}, " +
            "#{time, jdbcType=VARCHAR}, #{deviceData.mac, jdbcType=VARCHAR}, " +
            "#{deviceData.rssi, jdbcType=VARCHAR}, #{deviceData.range, jdbcType=VARCHAR}, " +
            "#{deviceData.ts, jdbcType=VARCHAR}, #{deviceData.tmc, jdbcType=VARCHAR}) ")
    void addMessage(String id, String time, DeviceData deviceData);
}
