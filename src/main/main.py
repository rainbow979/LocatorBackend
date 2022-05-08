import numpy as np
from database import DataBase, rssi2dis
import matplotlib.pyplot as plt

def calc_pt():
    #测试两圆交点公式实现是否正确
    i = 0
    j = 1
    
    AP = np.array([[0, 0], [0, 7]])
    
    L = AP[1] - AP[0]
    L = np.sqrt(L[0]**2+L[1]**2)
    print('L:', L)
    
    d = np.array([3.5, 3.5])
    
    
    t = L / 2 + (d[i]**2 - d[j]**2) / (2 * L)
    cent = AP[i] + (t / L) * (AP[j] - AP[i])
    h = np.sqrt(d[i]**2 - t**2) / L
    
    bias = np.array([AP[j][1] - AP[i][1], AP[i][0] - AP[j][0]]) * h
    p1 = cent + bias
    p2 = cent - bias
    
    for i in range(2):
        t = p1 - AP[i]
        t = np.sqrt(t[0]**2+t[1]**2)
        assert np.abs(t - d[i]) < 1e-5, t
        t = p2 - AP[i]
        t = np.sqrt(t[0]**2+t[1]**2)
        assert np.abs(t - d[i]) < 1e-5, t
        
def calc_dis(x, y):
    t = x - y
    return np.sqrt(np.sum(t**2))

def calc_Tri(AP, AP_dis, d):
    #三角定位算法
    PT = []
    for i in range(3):
        for j in range(i+1, 3):
            L = AP_dis[i+j-1]
            if L <= d[i] + d[j]:
                t = L / 2 + (d[i]**2 - d[j]**2) / (2 * L)
                cent = AP[i] + (t / L) * (AP[j] - AP[i])
                print(d[i], t)
                h = np.sqrt(d[i]**2 - t**2) / L
                
                bias = np.array([AP[j][1] - AP[i][1], AP[i][0] - AP[j][0]]) * h
                p1 = cent + bias
                p2 = cent - bias
                
                if np.abs(calc_dis(p1, AP[3-i-j]) - d[3-i-j]) < np.abs(calc_dis(p2, AP[3-i-j]) - d[3-i-j]):
                    PT.append(p1)
                else:
                    PT.append(p2)
    #print(AP, d, PT)                
    if len(PT) == 0:
        return None
    else:
        return np.mean(PT, 0)
        
        
def calc_LSM(AP, AP_dis, d):
    #最小二乘法
    X = np.zeros((2, 2))
    for i in range(2):
        for j in range(2):
            X[i, j] = 2 * (AP[i, j] - AP[-1, j])
    Y = np.zeros((2, 1))
    for i in range(2):
        Y[i, 0] = (AP[i, 0]**2) - (AP[-1, 0]**2) + (AP[i, 1]**2) - (AP[-1, 1]**2) + (d[-1]**2) - (d[i]**2)
    
    return np.matmul(np.matmul(np.linalg.inv(np.matmul(X.T, X)), X.T),Y).squeeze(1)
    
    
def get_dis():
    #获得数据
    db = DataBase()
    phone_mac = "c4:e1:a1:bf:cb:b3"
    router_ids = ["14:6B:9C:F4:04:3E", "14:6B:9C:F4:04:39", "14:6B:9C:F4:03:F1"]
    dis = []
    start_time = 35417
    end_time = 36590
    for router_id in router_ids:
        dis.append(db.query(phone_mac, router_id, start_time, end_time))
    return dis
    
def smooth(x, y):
    for i in range(len(x)):
        x[i] = np.mean(x[max(0, i-2):min(len(x), i+2)])
        y[i] = np.mean(y[max(0, i-2):min(len(y), i+2)])
    return x, y
    
def main():
    
    AP = [[0, 0], [3.6, 0], [0, 3]]         #三个WIFI探针
    AP = np.array(AP)
    true_point = [[1, -1], [1, 0], [1, 1], [1, 2], [1, 3], [1, 4], [1, 5], [2, 5], [3, 5], [4, 5], [5, 5]]
    true_point = np.array(true_point) * 0.6
    dis = []       #Traj的数据，包括距离
    dis = get_dis()
    
    AP_dis = [3.6, 3, 4.686]     #三个探针之间的距离
    AP_dis = np.array(AP_dis)
    trajx = []
    trajy = []
    for i in range(len(dis[0])):
        point = calc_Tri(AP, AP_dis, (dis[0][i], dis[1][i], dis[2][i]))
        if point is not None:
            trajx.append(point[0])
            trajy.append(point[1])
    trajx, trajy = smooth(trajx, trajy)
    plt.plot(np.array(trajx), np.array(trajy), 'r-*')
    trajx = []
    trajy = []
    for i in range(len(dis[0])):
        point = calc_LSM(AP, AP_dis, (dis[0][i], dis[1][i], dis[2][i]))
        trajx.append(point[0])
        trajy.append(point[1])
    trajx, trajy = smooth(trajx, trajy)
    plt.plot(np.array(trajx), np.array(trajy), 'y-*')
    plt.plot(np.array([p[0] for p in true_point]), np.array([p[1] for p in true_point]), 'g-*')
    for i in range(3):
        plt.plot([AP[i, 0]], [AP[i, 1]], 'bo')
    plt.show()

if __name__ == "__main__":
    main()