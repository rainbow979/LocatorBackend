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
            "#{deviceData.rssi, jdbcType=VARCHAR}, #{deviceData.range, jdbcType=VARCHAR})")
    void addMessage(String id, String time, DeviceData deviceData);

    @Insert("insert into message " +
            "(device_id, time, mac, rssi, distance) values " +
            "('0029c591', 'Tue Feb 21 08:13:31 2017', 'a4:56:02:61:7f:57', '-91', '91.5')")
    void testAdd();
}
