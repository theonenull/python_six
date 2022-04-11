import hashlib
import threading
import time
import tkinter
from tkinter import scrolledtext, PhotoImage, Tk, Label, Button, StringVar, Entry, INSERT, messagebox, END
from tkinter.messagebox import showinfo
from tkinter.scrolledtext import ScrolledText
import pymysql as mysql
import requests
import socket  # 导入 socket 模块
import io
from PIL import Image, ImageTk
import tkinter as tk
import requests

massage = ''
userName = ''
userPhoto = ''
userMotto = ''
py = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8')
cursor = py.cursor()
cursor.execute("CREATE DATABASE IF NOT EXISTS data_socket DEFAULT CHARACTER SET utf8")
# 进入数据库data_socket
dy = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8',
                   database='data_socket')
cursor = dy.cursor()


def link_socket(s, arr):
    while True:
        inp = input("请输入要发送的信息： ").strip()
        if not inp:
            continue
        s.sendall(inp.encode())
        if inp == "exit":
            print("结束通信！")
            break
        server_reply = s.recv(1024).decode()
        print(server_reply)
    s.close()


def getFirstNamePassWord():
    firstname = str(firstname_entry.get())
    password = str(password_entry.get())
    h1 = hashlib.md5()
    h1.update((firstname + password).encode('utf-8'))
    cookie = h1.hexdigest()
    cursor.execute('SELECT * FROM userpass WHERE passCode="%s"' % cookie)
    print(cookie)
    result = cursor.fetchone()
    if result is not None and result[0] == firstname:

        label1.destroy()
        label2.destroy()
        label3.destroy()
        firstname_entry.destroy()
        password_entry.destroy()
        button_register.destroy()
        button_login.destroy()
        cursor.execute('select * from usercode where firstName="%s"' % result[0])
        resultForCode = cursor.fetchone()
        cursor.execute('select * from usermassage where userCode = "%s"' % resultForCode[1])
        userMassage = cursor.fetchone()
        global userName
        userName = userMassage[1]
        global userPhoto
        userPhoto = userMassage[2]
        global userMotto
        userMotto = userMassage[3]
        create()

    else:
        result = showinfo('提示', '密码错误')
def register():
    firstname = str(firstname_entry.get())
    password = str(password_entry.get())
    # 检验账号是否唯一
    cursor.execute('SELECT * FROM userpass WHERE firstName="%s"' % firstname)
    result = cursor.fetchone()
    if result is not None:
        result = showinfo('提示', '用户名已存在')
    else:
        h2 = hashlib.md5()
        h2.update((firstname + password).encode('utf-8'))
        h3 = hashlib.md5()
        h3.update(firstname.encode('utf-8'))
        # 设置cookie进session，便于下次验证
        cursor.execute('INSERT INTO userpass (firstName, passCode) VALUES ("%s","%s")' % (firstname, h2.hexdigest()))
        cursor.execute('INSERT INTO usercode (firstName, userCode) VALUES ("%s","%s")' % (firstname, h3.hexdigest()))
        cursor.execute(
            'INSERT INTO usermassage (userCode, userName, userPhoto, userMotto) VALUES ("%s","%s","%s","%s")' % (
                h3.hexdigest(), "默认名字", "https://www.keaidian.com/uploads/allimg/190424/24110307_6.jpg", "写下你的个性签名吧"))
        dy.commit()
        result = showinfo('提示', '注册成功，即将跳转')
        getFirstNamePassWord()

def create():
    def JieShu():
        messagebox.showwarning(title='你确定退出吗？', message='刚才你点击了关闭按钮')
        s.send("exit".encode('utf-8'))
        exit(0)

    def recv(sock):
        sock.send(userName.encode('utf-8'))
        # sock.send(userPhoto.encode('utf-8'))
        # sock.send(userMotto.encode('utf-8'))
        while True:
            data = sock.recv(1024).decode()
            # 加一个时间戳
            if (data == "EXIT"):
                sock.close()
            time_tuple = time.localtime(time.time())
            time_str = ("{}点{}分".format(time_tuple[3], time_tuple[4]))
            print(str(data))
            global scr
            scr.config(state='normal')
            # scr.insert(INSERT, time_str)
            # scr.insert(INSERT, '\n')
            scr.insert(INSERT, data)
            # scr.insert(INSERT, '\n')
            # scr.insert(INSERT, '\n')
            print("succsee")
            scr.see(END)
            scr.config(state='disabled')

    def send_massage():
        if scr_massage.get(1.0, 'end') == '\n':
            print('输入为空')
        else:
            message = str(scr_massage.get(1.0, 'end'))[:-1]
            scr_massage.delete(1.0, END)
            data = message.encode('utf-8')
            s.send(data)

    # 图片链接
    url = userPhoto
    # 下载图片数据
    image_bytes = requests.get(url).content
    # 将数据存放到data_stream中
    data_stream = io.BytesIO(image_bytes)
    # 转换为图片格式
    pil_image = Image.open(data_stream)
    # 获取图片的宽度和高度
    w, h = pil_image.size
    # 获取图片的文件名
    fname = url.split('/')[-1]
    sf = "{} ({}x{})".format(fname, w, h)
    pil_image=pil_image.resize((150, 150), Image.ANTIALIAS)
    tk_image = ImageTk.PhotoImage(pil_image)
    s = socket.socket()  # 创建 socket 对象
    host = socket.gethostname()  # 获取本地主机名
    port = 9999  # 设置端口号
    s.connect(('127.0.0.1', port))
    tr = threading.Thread(target=recv, args=(s,), daemon=True)
    # daemon=True 表示创建的子线程守护主线程，主线程退出子线程直接销毁
    tr.start()
    win.geometry('1200x600-200-100')
    photo = PhotoImage(file='灯泡.png')
    # 图片
    lab = Label(win, image=tk_image, height=150, width=150, borderwidth=4, relief="sunken")
    lab.place(x=0, y=5)
    #名字
    textName = tkinter.Text(win, state="disabled")
    textName.place(x=2, y=175, height=25, width=150)
    textName.config(state='normal')
    textName.insert(INSERT, userName)
    textName.config(state='disabled')
    # 个人签名
    text = tkinter.Text(win, state="disabled")
    text.place(x=2, y=200, height=50, width=150)
    text.config(state='normal')
    text.insert(INSERT, userMotto)
    text.config(state='disabled')
    # 聊天室名字
    Name_room = Label(win, text="聊天室", bg="#7CCD7C", borderwidth=0, font=("隶书", 20))
    Name_room.place(x=155, y=0, width=800, height=50)
    # 绑定退出
    win.protocol("WM_DELETE_WINDOW", JieShu)
    # 安放聊天框
    scr.place(x=155, y=50, width=800, height=400)
    # 输入框
    scr_massage = ScrolledText(win, font=("隶书", 12))
    scr_massage.place(x=155, width=800, y=450, height=150)
    # 聊天成员
    scr_member = ScrolledText(win, font=("隶书", 18), state='disabled')
    scr_member.place(x=955, y=0, width=245, height=600)
    # 发送键
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
button_register = Button(height=1, width=20, command=register, text="注册", borderwidth=1)
button_register.pack()
scr = scrolledtext.ScrolledText(win,
                                font=("隶书", 18), state='disabled')

win.mainloop()
