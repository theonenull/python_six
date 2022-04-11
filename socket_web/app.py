import time
from datetime import timedelta
import hashlib
import pymysql as mysql
from flask import Flask, render_template, request, make_response, redirect, url_for, session, jsonify
from flask_socketio import SocketIO, emit
import json
import datetime

app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret_key'
app.config['PERMANENT_SESSION_LIFETIME'] = timedelta(days=7)

py = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8')
cursor = py.cursor()
cursor.execute("CREATE DATABASE IF NOT EXISTS data_socket DEFAULT CHARACTER SET utf8")
# 进入数据库data_socket
dy = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8',
                   database='data_socket')
cursor = dy.cursor()

# 身份验证表
cursor.execute(
    'CREATE TABLE IF NOT EXISTS userPass (firstName varchar(50) unique primary key ,'
    'passCode varchar(50))')
# 数据库通行身份码
cursor.execute('CREATE TABLE IF NOT EXISTS userCode (firstName varchar(50) ,userCode varchar(50))')

# 个人信息
cursor.execute(
    'CREATE TABLE IF NOT EXISTS userMassage(userCode varchar(50),userName varchar(50),userPhoto varchar(200),userMotto varchar(200))')
# 好友列表
cursor.execute(
    'CREATE TABLE IF NOT EXISTS userFriend(friendCode varchar(50),frinedname varchar(50),ownerCode varchar(50),owner varchar(50))')
# 消息情况
cursor.execute(
    'CREATE TABLE IF NOT EXISTS userMassage(massage varchar(200),userPost varchar(50),userGet varchar(50),postTime datetime(0), massageType varchar(20),massageSpace varchar(50))')
# 好友申请
cursor.execute('CREATE TABLE IF NOT EXISTS friendApply(userPost varchar(50),userGET varchar(50),getTime datetime(0))')

socketio = SocketIO()
socketio.init_app(app, cors_allowed_origins='*')

name_space = '/talkRoomData'

print("time:" + str(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')))


@app.route('/')
def index():
    print(str(session))
    return render_template('login.html')


# 登录解析数据
@app.route('/login', methods=['POST', 'GET'])
def push_once():
    firstname = request.form.get("firstname")
    password = request.form.get("password")
    # 通过加密数据来获取账号，进行对比
    h1 = hashlib.md5()
    h1.update((firstname + password).encode('utf-8'))
    cookie = h1.hexdigest()
    cursor.execute('SELECT * FROM userpass WHERE passCode="%s"' % cookie)
    print(cookie)
    result = cursor.fetchone()
    print("resultFromMysql:" + str(result))
    print("firstname:" + str(result[1]))
    if result is not None and result[0] == firstname:
        session[h1.hexdigest()] = firstname
        data = {
            "status": "ALLOW",
            "cookie": cookie
        }
        return jsonify(data)
    else:
        data = {
            "status": "NOTALLOWED"
        }
        return jsonify(data)


# 注册解析数据
@app.route('/register', methods=['POST', 'GET'])
def register():
    firstname = request.form.get("firstname")
    password = request.form.get("password")
    # 检验账号是否唯一
    cursor.execute('SELECT * FROM userpass WHERE firstName="%s"' % firstname)
    result = cursor.fetchone()
    if result is not None:
        data = {
            'data': "ALREADY"
        }
        return jsonify(data)
    else:
        h2 = hashlib.md5()
        h2.update((firstname + password).encode('utf-8'))
        h3 = hashlib.md5()
        h3.update(firstname.encode('utf-8'))
        # 设置cookie进session，便于下次验证
        session[h2.hexdigest()] = firstname
        cursor.execute('INSERT INTO userpass (firstName, passCode) VALUES ("%s","%s")' % (firstname, h2.hexdigest()))
        cursor.execute('INSERT INTO usercode (firstName, userCode) VALUES ("%s","%s")' % (firstname, h3.hexdigest()))
        cursor.execute(
            'INSERT INTO usermassage (userCode, userName, userPhoto, userMotto) VALUES ("%s","%s","%s","%s")' % (
                h3.hexdigest(), "默认名字", "http://127.0.0.1:5000/static/10001.jpg", "写下你的个性签名吧"))
        dy.commit()

        result = {
            "cookie": h2.hexdigest(),
            "data": "ALLOW"
        }
        return jsonify(result)


# 进入聊天室
@app.route("/talkRoom", methods=['GET', 'POST'])
def talk():
    print("---------------talkRoom----------------------")
    if request.method == 'GET':
        return render_template('talkRoom.html')
    if request.method == 'POST':
        cookie = request.form.get('cookie')
        if session.get(cookie) is None:
            print("cookie:" + cookie)
            return "USELESS"
        else:
            cursor.execute('SELECT * FROM userpass WHERE passCode="%s"' % cookie)
            hForFirstName = hashlib.md5()
            hForFirstName.update((session.get(cookie)).encode('utf-8'))
            codeForUser = hForFirstName.hexdigest()
            cursor.execute('SELECT * FROM usermassage WHERE userCode="%s"' % codeForUser)
            dataForUser = cursor.fetchone()
            cursor.execute('SELECT * FROM userfriend WHERE ownerCode="%s"' % codeForUser)
            dataForFriendOfUser = cursor.fetchall()
            dataForReturn = {
                "userName": dataForUser[1],
                "userPhoto": dataForUser[2],
                "userMotto": dataForUser[3],
                "dataFriend": dataForFriendOfUser
            }
            name = {
                "name": dataForUser[1],
                "time": str(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')),
                "code": dataForUser[0]
            }
            print("here")
            socketio.emit("in", name, namespace=name_space)
            return jsonify(dataForReturn)


# 聊天室访问成员

# 个人主页
@app.route('/person', methods=['POST', 'GET'])
def person():
    if request.method == "GET":
        return render_template("person.html")
    if request.method == "POST":
        userCode = request.form.get("cookie")
        friendCode = request.form.get("friendCode")
        if friendCode == userCode:
            return render_template("person.html")
        else:
            return render_template("person2.html")


# 申请个人页面信息（个人）(无用)
@app.route('/person/massage', methods=['POST', 'GET'])
def getMassage():
    cookie = request.form.get("cookie")
    userName = session.get(cookie)
    cursor.execute('SELECT * FROM usercode WHERE firstName="%s"' % userName)
    user = cursor.fetchone()
    cursor.execute('SELECT * FROM usermassage where userCode="%s"' % user[1])
    result = cursor.fetchone()
    cursor.execute('select * from userfriend where ownerCode="%s"' % user[1])
    result2 = cursor.fetchall()
    resultForReturn = {
        "userName": result[1],
        "userPhoto": result[2],
        "userMotto": result[3],
        "userFriend": result2
    }
    return result2


# 查看朋友信息
@app.route('/friend', methods=['POST', 'GET'])
def friend():
    if request.method == 'GET':
        friendCode = request.args.get('friendCode')
        myCode = request.args.get('myCode')
        myCookie = request.args.get('myCookie')

        if myCookie is not None:
            cursor.execute('select * from userpass where passCode="%s"' % myCookie)
            result1 = cursor.fetchone()
            cursor.execute('select * from usercode where firstName="%s"' % result1[0])
            resultOfUserCode=cursor.fetchone()
            cursor.execute('select * from usermassage where userCode="%s"'% resultOfUserCode[1])
            resultOfUserMassage = cursor.fetchone()
            myCode = resultOfUserCode[1]
            print('myCode:'+str(myCode))
            print('friendCode:'+str(friendCode))
        cursor.execute('select * from userfriend where friendCode="%s" and ownerCode="%s"' % (friendCode, myCode))

        if cursor.fetchone() is not None:
            cursor.execute('select * from userfriend where friendCode="%s" and ownerCode="%s"' % (friendCode, myCode))
            result = cursor.fetchone()
            cursor.execute('select * from usermassage where userCode="%s"' % result[0])
            result2 = cursor.fetchone()
            dataForReturn = {
                'userCode': result2[0],
                'userName': result2[1],
                'userPhoto': result2[2],
                'userMotto': result2[3]
            }
            return render_template('friend.html', data=dataForReturn)
        else:
            cursor.execute('select * from usermassage where userCode="%s"' % friendCode)
            resultOfFriendMassage = cursor.fetchone()
            data = {
                'FriendCode': resultOfFriendMassage[0],
                'FriendName': resultOfFriendMassage[1],
                'FriendPhoto': resultOfFriendMassage[2],
                'FriendMotto': resultOfFriendMassage[3],
                'myCode': myCode
            }
            return render_template('person2.html', data=data)
    if request.method == 'POST':
        code = request.form.get('code')
        return render_template('')


# 聊天接送信息
@app.route("/roomdata", methods=['POST', 'GET'])
def getTalkRoom():
    print("talkRoomData-------------------------------")
    print("request.form:" + str(request.form))
    data = request.form.get('data')
    userCode = request.form.get('user')
    cursor.execute('select * from userpass where passCode="%s"' % userCode)
    result = cursor.fetchone()
    username = result[0]
    cursor.execute('select * from usercode where firstName="%s"' % username)
    find_result = cursor.fetchone()
    code = find_result[1]
    cursor.execute('select * from usermassage where userCode="%s"' % code)
    find_result2 = cursor.fetchone()
    print(str(data))
    print("find_result:" + str(find_result))
    res = {
        "data": data,
        "photo": find_result2[2],
        "name": find_result2[1],
        "time": str(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
    }
    socketio.emit("roomMassage", res, namespace=name_space)
    print("2talkRoomData-------------------------------")
    return "111111111"


# 获取用户信息
@app.route('/userMassage', methods=['POST', 'GET'])
def getUserData():
    userCode = request.form.get('cookie')
    cursor.execute('select * from userpass where passCode="%s"' % userCode)
    result = cursor.fetchone()
    print('result:' + str(result))
    cursor.execute('select * from usercode where firstName="%s"' % result[0])
    result2 = cursor.fetchone()
    cursor.execute('select * from usermassage where userCode="%s"' % result2[1])
    userMassage = cursor.fetchone()
    print(str(userMassage))
    cursor.execute('select * from userfriend where ownerCode="%s"' % result2[1])
    userFriend = cursor.fetchall()
    dataForReturn = {
        'userCode': userMassage[0],
        'userName': userMassage[1],
        'userPhoto': userMassage[2],
        'userMotto': userMassage[3],
        'userFriend': userFriend
    }
    return dataForReturn


# 修改个人信息
@app.route('/changedata', methods=['POST', 'GET'])
def getData():
    if request.method == 'GET':
        return render_template('change.html')
    if request.method == 'POST':
        try:
            print(str(request.form))
            userName = request.form.get('userName')
            userCode = request.form.get('userCode')
            userPhoto = request.form.get('userPhoto')
            userMotto = request.form.get('userMotto')
            cursor.execute('select * from userpass where passCode="%s"' % userCode)
            result = cursor.fetchone()
            print('result:' + str(result))
            cursor.execute('select * from usercode where firstName="%s"' % result[0])
            result2 = cursor.fetchone()
            cursor.execute(
                'UPDATE usermassage SET userName = "%s", userPhoto = "%s" ,userMotto="%s" WHERE userCode="%s"' % (
                    userName, userPhoto, userMotto, result2[1]))
            cursor.execute('UPDATE userfriend SET frinedname = "%s" WHERE friendCode="%s"' % (
                userName, result2[1]))
            cursor.execute('UPDATE userfriend SET owner = "%s" WHERE ownerCode="%s"' % (
                userName, result2[1]))
            dy.commit()
            return "YES"
        except (Exception):
            return "NO"

    return "yes"


@socketio.on('connect', namespace=name_space)
def connected_msg():
    emit('123',
         {'data': "351172", 'count': 1})
    print('client connected.')


@socketio.on('disconnect', namespace=name_space)
def disconnect_msg():
    print('client disconnected.')


@socketio.on('my_event', namespace=name_space)
def mtest_message(message):
    print(message)
    emit('myevent',
         {'data': message['data'], 'count': 1}, namespace=name_space)


@socketio.on('my event')
def handle_my_custom_event(json):
    print('received json: ' + str(json))


if __name__ == '__main__':
    socketio.run(app, host='0.0.0.0', port=5000, debug=True)
