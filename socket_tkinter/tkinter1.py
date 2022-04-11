import threading
import time
import tkinter
from tkinter import scrolledtext, PhotoImage, Tk, Label, Button, StringVar, Entry, INSERT
from tkinter.scrolledtext import ScrolledText

import requests
import socket  # 导入 socket 模块

massage = ''
name = ''


def recv(sock):
    sock.send(name.encode('utf-8'))
    while True:
        data = sock.recv(1024)
        # 加一个时间戳
        time_tuple = time.localtime(time.time())
        str = ("{}点{}分".format(time_tuple[3], time_tuple[4]))


def link_socket(s, arr):
    while True:
        inp = input("请输入要发送的信息： ").strip()
        if not inp:  # 防止输入空信息，导致异常退出
            continue
        s.sendall(inp.encode())
        if inp == "exit":  # 如果输入的是‘exit’，表示断开连接
            print("结束通信！")
            break
        server_reply = s.recv(1024).decode()
        print(server_reply)
    s.close()


def getFirstNamePassWord():
    firstname = str(firstname_entry.get())
    password = str(password_entry.get())

    if firstname == "1" and password == "1":
        label1.destroy()
        label2.destroy()
        label3.destroy()
        firstname_entry.destroy()
        password_entry.destroy()
        button_register.destroy()
        button_login.destroy()
        create()


def create():
    def recv(sock):
        sock.send(name.encode('utf-8'))
        while True:
            data = sock.recv(1024).decode()
            # 加一个时间戳
            if(data=="EXIT"):
                sock.close()
            time_tuple = time.localtime(time.time())
            time_str = ("{}点{}分".format(time_tuple[3], time_tuple[4]))
            print(str(data))
            scr.config(state='normal')
            scr.insert(INSERT, time_str)
            scr.insert(INSERT, '\n')
            scr.insert(INSERT, data)
            scr.insert(INSERT,'\n')
            scr.insert(INSERT,'\n')
            print("succsee")
            scr.config(state='disabled')


    def send_massage():
        if scr_massage.get(1.0, 'end') == '\n':
            print('输入为空')
        else:
            message = str(scr_massage.get(1.0, 'end'))[:-1]
            data = message.encode('utf-8')
            s.send(data)

    s = socket.socket()  # 创建 socket 对象
    host = socket.gethostname()  # 获取本地主机名
    port = 9999  # 设置端口号
    s.connect(('127.0.0.1', port))
    tr = threading.Thread(target=recv, args=(s,), daemon=True)
    # daemon=True 表示创建的子线程守护主线程，主线程退出子线程直接销毁
    tr.start()
    win.geometry('1200x600-200-100')
    photo = PhotoImage(file='灯泡.png')
    # 将图片放在主窗口的右边
    lab = Label(win, image=photo, height=150, width=150, borderwidth=4, relief="sunken")
    lab.place(x=0, y=0)
    Name_room = Label(win, text="聊天室", bg="#7CCD7C", borderwidth=0, font=("隶书", 20))
    Name_room.place(x=155, y=0, width=800, height=50)
    scr = scrolledtext.ScrolledText(win,
                                    font=("隶书", 18),state='disabled')
    scr.place(x=155, y=50, width=800, height=400)

    scr_massage = ScrolledText(win, font=("隶书", 8))
    scr_massage.place(x=155, width=800, y=450, height=150)
    scr_member = ScrolledText(win, font=("隶书", 18), state='disabled')
    scr_member.place(x=955, y=0, width=245, height=600)
    button_send = Button(text="发送", command=lambda: send_massage())
    button_send.place(x=800, y=540, width=100, height=50)
    win.mainloop()


win = Tk()
win.title("聊天室登录")
win.geometry('300x200+500+300')
massageForSend = StringVar()
massageForSend.set('')
img_gif = PhotoImage(file='灯泡.png')
label1 = Label(win, text="账号", font=('微软雅黑', 10))
label1.place(x=28, y=38)
label2 = Label(win, text="密码", font=('微软雅黑', 10))
label2.place(x=28, y=80)
label3 = Label(win, text="登录", font=('微软雅黑', 20))
label3.pack()
firstname_entry = Entry(win, exportselection=0)
firstname_entry.pack()
password_entry = Entry(win, exportselection=0)
password_entry.pack(pady=20)
button_login = Button(height=1, width=20, command=getFirstNamePassWord, text="登录", borderwidth=1)
button_login.pack()
button_register = Button(height=1, width=20, command=getFirstNamePassWord, text="注册", borderwidth=1)
button_register.pack()

win.mainloop()
