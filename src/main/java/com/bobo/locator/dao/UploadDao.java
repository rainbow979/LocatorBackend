package com.bobo.locator.dao;

import com.bobo.locator.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UploadDao {
    @Insert("insert into message " +
            "(gateway_id, time, data) values " +
            "(#{gatewayId, jdbcType=VARCHAR}, #{time, jdbcType=VARCHAR}, #{data, jdbcType=VARCHAR})")
    void addMessage(Message message);
}
