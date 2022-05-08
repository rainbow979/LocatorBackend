import numpy as np
import pymysql
import os
from collections import deque

def rssi2dis(rssi):
    return 10**((np.abs(rssi)-51.381) / (10*4.001))
    


class DataBase:
    def __init__(self):
        self.db = pymysql.connect(
           host="localhost",  # IP地址
           port=3306,  # 端口号
           user="locator",  # 用户名
           password="locatorPassword",  # 密码
           db="locator",  # 数据库名
           charset="utf8"  # 字符集编码 
        )
        
        
    def query(self, phone_mac, router_id, start_time, end_time):
        cursor = self.db.cursor()
        sql = "select * from message where mac=%s and router_id=%s"
        cursor.execute(sql, [(phone_mac, router_id)])
        results = cursor.fetchall()
        dis = []
        last_update_time = start_time
        q = deque()
        for row in results:
            time = self.parse(row[1])
            rssi = float(row[3])            
            if time >= start_time and time <= end_time:
                q.append([time, rssi])
                while time - q[0][0] > 30:
                    q.popleft()
                #deal with the lack of data
                while (time - last_update_time) > 10:
                    dis.append(rssi2dis(np.array(q)[:, 1].mean()))
                    last_update_time += 10
                
        cursor.close()
        return dis
                
    
    def parse(self, s):
        t = s.split(" ")[4]
        t = t.split(":")
        time = 0
        for i in t:
            time = time * 60 + float(i)
        return time
        
    def close(self):
        self.db.close()
        
        

