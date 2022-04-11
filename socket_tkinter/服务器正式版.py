import socket
import threading
import time

import flask
from flask import Flask, request

list_client = []


def link_handler(link, client):
    print("服务器开始接收来自[%s:%s]的请求...." % (client[0], client[1]))
    userName = link.recv(20000).decode()
    time_tuple = time.localtime(time.time())
    time_str = ("{}点{}分".format(time_tuple[3], time_tuple[4]))
    for i in list_client:
        i[0].send((time_str+'    '+userName+"进入聊天室"+'\n'+'\n').encode())
    while True:  # 利用一个死循环，保持和客户端的通信状态
        client_data = link.recv(20000).decode()
        if client_data == "exit":
            print("结束与[%s:%s]的通信..." % (client[0], client[1]))
            list_client.remove([link, client])
            print('list:'+str(list_client))
            link.send('EXIT'.encode())
            break
        print("来自[%s:%s]的客户端向你发来信息：%s" % (client[0], client[1], client_data))
        for i in list_client:
            print('list:' + str(list_client))
            time_tuple = time.localtime(time.time())
            time_str = ("{}点{}分".format(time_tuple[3], time_tuple[4]))
            i[0].send((time_str+'('+userName+')'+'\n'+client_data+'\n'+'\n').encode())
    link.close()


ip_port = ('127.0.0.1', 9999)
sk = socket.socket()
sk.bind(ip_port)
sk.listen(5)

print('启动socket服务，等待客户端连接...')

while True:
    conn, address = sk.accept()
    print(str(conn))
    list_client.append([conn, address])
    print(str(list_client))
    t = threading.Thread(target=link_handler, args=(conn, address))
    t.start()
