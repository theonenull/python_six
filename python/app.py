import time

from flask import Flask, request, redirect, url_for, session, render_template
import pymysql as mysql
import datetime

app = Flask(__name__)
app.config['SECRET_KEY'] = "sdfklas0lk42j"
py = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8')
cursor = py.cursor()
cursor.execute("CREATE DATABASE IF NOT EXISTS web_table DEFAULT CHARACTER SET utf8")
# 进入数据库schedule
dy = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8',
                   database='web_table')
cursor = dy.cursor()
cursor.execute(
    'CREATE TABLE IF NOT EXISTS person (Id int(10) primary key auto_increment ,firstname varchar(20) unique ,'
    'pin varchar(20),statue varchar(1),picture varchar(200))')
cursor.execute('create table if not EXISTS list_business (id int(10) primary key auto_increment,name varchar(20))')
cursor.execute('create table if not EXISTS list_user (id int(10) primary key auto_increment,name varchar(20))')
cursor.execute('create table if not EXISTS list_rider (id int(10) primary key auto_increment,name varchar(20))')
cursor.execute(
    'create table if not EXISTS list_order (id int(10) primary key auto_increment,user_name varchar(20),business_name varchar(20),rider_name varchar(20),time datetime)')


@app.route('/')
def hello_world():
    return redirect(url_for('login'))


datetime.time()


# 登录
@app.route('/login', methods=['GET', 'POST'])
def login():
    username = request.form.get("username", None)
    password = request.form.get("password", None)
    print(1)
    print(username)
    print(password)
    print(1)
    if username is not None:
        find = 'select * from person where firstname="%s"' % username
        cursor.execute(find)
        result = cursor.fetchone()
        print(result)
        if result is not None:
            if str(result[2]) == password:
                session['username'] = str(result[1])
                print("session=" + session['username'])
                if result[3] == '0':
                    return redirect(url_for('business'))
                elif result[3] == '1':
                    return redirect(url_for('rider'))
                elif result[3] == '3':
                    return redirect(url_for('manager'))
                else:
                    return redirect(url_for('user'))

    return render_template('login.html')


# 注册
@app.route('/register', methods=['GET', 'POST'])
def registered():
    username = request.form.get('username', default=None)
    password = request.form.get('password', default=None)
    picture = request.form.get('picture', default=None)
    statue = request.form.get('statue', default=2)

    if username is not None and picture is not None and password is not None:
        check = 'select * from person'
        cursor.execute(check)
        result = cursor.fetchall()
        for i in result:
            if i[1] == username:
                return '''用户名已被占用'''
        if statue == "2":
            insert_user = 'insert into list_user (name) value ("%s")' % username
            cursor.execute(insert_user)
            insert_person = 'insert into person (firstname, pin, statue,picture) value ("%s","%s","%s","%s")' % (
                username, password, str(statue), picture)
            cursor.execute(insert_person)
            create_user_table_care = 'create table ' + username + '_care(name varchar(100),business varchar(20) )'
            cursor.execute(create_user_table_care)
            create_user_table_friend = 'create table ' + username + '_friend(name varchar(100))'
            cursor.execute(create_user_table_friend)
            create_user_table_order = 'create table ' + username + '_order(name varchar(100),business varchar(20) )'
            cursor.execute(create_user_table_order)
            return render_template('success.html')
        elif statue == "0":
            insert_business = 'insert into list_business(name) value ("%s")' % username
            cursor.execute(insert_business)
            insert_person = 'insert into person (firstname, pin, statue) value ("%s","%s",%s)' % (
                username, password, str(statue))
            cursor.execute(insert_person)
            create_business_table_goods = 'create table ' + username + '_goods (name varchar(25),price Int(10))'
            cursor.execute(create_business_table_goods)
            create_business_table_order = 'create table ' + username + '_order (name varchar(25),username varchar(20) ,number int(10)) '
            cursor.execute(create_business_table_order)
            return render_template('success.html')
        elif statue == "1":
            insert_rider = 'insert into list_rider(name) value ("%s")' % username
            cursor.execute(insert_rider)
            insert_person = 'insert into person (firstname, pin, statue) value ("%s","%s",%s)' % (
                username, password, str(statue))
            cursor.execute(insert_person)
            create_rider_table_order = 'create table ' + username + '_order (name varchar(25),username varchar(20) ,price Int(10),time datetime ,number int(10)) '
            cursor.execute(create_rider_table_order)
            return render_template('success.html')

    return render_template('registered.html')


# 用户主页
@app.route('/user', methods=['GET', 'POST'])
def user():
    if session.get('username') is not None:
        session_name = session.get('username')
        find_picture = 'select * from person where firstname="%s"' % session_name

        cursor.execute(find_picture)
        a = cursor.fetchone()
        picture = a[4]
        find = 'select * from list_business'
        cursor.execute(find)
        list_for_business = cursor.fetchall()
        list_for_html = {
            'user': session_name,
            'list': list_for_business,
            'picture': picture
        }
        return render_template('user.html', list_for_html=list_for_html)
    else:
        return redirect(url_for('login'))


# 商家主页
@app.route('/business', methods=['GET', 'POST'])
def business():
    if session.get('username') is not None:
        session_name = session.get('username')
        find_picture = 'select * from person where firstname="%s"' % session_name
        cursor.execute(find_picture)
        a = cursor.fetchone()
        picture = a[4]
        find = 'select * from %s_order' % session_name
        cursor.execute(find)
        list_for_business = cursor.fetchall()
        find1 = 'select * from %s_goods' % session_name
        cursor.execute(find1)
        list_for_goods = cursor.fetchall()
        list_for_html = {
            'user': session_name,
            'list': list_for_business,
            'picture': picture,
            'list_for_goods': list_for_goods
        }
        return render_template('business.html', list_for_html=list_for_html)
    else:
        return redirect(url_for('login'))


# 商家详情
@app.route('/business/<string:name>/<string:name2>', methods=['GET'])
def find_business(name, name2):
    find_business_detail = 'select * from ' + name + '_goods'
    cursor.execute(find_business_detail)
    result = cursor.fetchall()
    list_for_html = {
        'list': result,
        'user': name2,
        'business': name
    }
    return render_template('business_detail.html', list_for_html=list_for_html)


# 用户收藏
@app.route('/user_care/<string:business>/<string:user>')
def care_user(business, user):
    care = 'insert into ' + user + '_care (name) values ("%s")' % business
    cursor.execute(care)
    dy.commit()
    return render_template('like_success.html')


# 用户注销
@app.route('/user/<string:name>', methods=['POST', 'GET', 'DELETE'])
def delete_user(name):
    delete = 'delete from list_user where name="%s"' % name
    cursor.execute(delete)
    delete2 = 'delete from person where firstname="%s"' % name
    cursor.execute(delete2)
    delete3 = 'drop table ' + name + '_care'
    cursor.execute(delete3)
    delete4 = 'drop table ' + name + '_friend'
    cursor.execute(delete4)
    delete5 = 'drop table ' + name + '_order'
    cursor.execute(delete5)
    dy.commit()
    return render_template('success.html')


# 用户购买
@app.route('/user/<string:user>/business/<string:business>/<string:good>')
def buy(user, business, good):
    buy_user = 'insert into %s_order (name,business) values ("%s","%s")' % (user, good, business)
    cursor.execute(buy_user)
    buy_business = 'insert into %s_order(name,username,number) values ("%s","%s","%s")' % (business, good, user, 1)
    cursor.execute(buy_business)
    buy_all = 'insert into list_order (user_name, business_name) VALUES ("%s","%s")' % (
        user, business)
    cursor.execute(buy_all)
    dy.commit()
    return '''购买成功'''


# 用户好友
@app.route('/user_friend/<string:user>')
def user_friend(user):
    find_friend = 'select * from ' + user + '_friend'
    cursor.execute(find_friend)
    friends = cursor.fetchall()
    list = {
        'friend': friends,
        'user': user
    }
    return render_template('friends.html', list=list)


# 用户订单
@app.route('/user_order/<string:user>')
def user_order(user):
    user_order = 'select * from ' + user + '_order'
    cursor.execute(user_order)
    result = cursor.fetchall()
    list_html = {
        'user': user,
        'list': result
    }
    return render_template('user_order.html', list_html=list_html)


# 用户收藏页
@app.route('/user_care/<string:user>')
def user_care(user):
    user_care = 'select * from ' + user + '_care'
    cursor.execute(user_care)
    result = cursor.fetchall()
    list_html = {
        'user': user,
        'list': result
    }
    return render_template('user_care.html', list_html=list_html)


# 删除收藏
@app.route('/delete/user_care/<string:business>/<string:user>')
def delete_care(user, business):
    delete = 'delete from %s_care where name="%s"' % (user, business)


# 添加菜品
@app.route('/business_add/<string:name>', methods=['POST', 'GET'])
def add_good(name):
    good = request.form.get('good', default=None)
    price = request.form.get('price', default=None)
    if good is not None and price is not None:
        add_food = 'insert into %s_goods(name, price) VALUES ("%s","%s")' % (name, good, price)
        cursor.execute(add_food)
        dy.commit()
        return render_template('success.html')

    return render_template('business_add.html')

#骑士主页
@app.route('/rider', methods=['GET', 'POST'])
def rider():
    if session.get('username') is not None:
        session_name = session.get('username')
        find_picture = 'select * from person where firstname="%s"' % session_name
        cursor.execute(find_picture)
        a = cursor.fetchone()
        picture = a[4]
        find = 'select * from list_order where rider_name=%s'%session_name
        cursor.execute(find)
        list_for_business = cursor.fetchall()
        find2 = 'select * from list_order where rider_name is NULL'
        cursor.execute(find2)
        result = cursor.fetchall()
        list_for_html = {
            'user': session_name,
            'list': list_for_business,
            'picture': picture,
            'all_order': result
        }
        return render_template('rider.html', list_for_html=list_for_html)
    else:
        return redirect(url_for('login'))


# 接单
@app.route('/rider/<string:rider>/business/<string:business>')
def receive(rider, business):
    put = 'update list_order set rider_name=%s where business_name=%s' % (rider, business)
    cursor.execute(put)
    dy.commit()
    return render_template('like_success.html')

#管理员
@app.route('/root')
def root():
    find='select * from web_table.list_order'
    cursor.execute(find)
    list_order=cursor.fetchall()
    find1='select * from web_table.person'
    cursor.execute(find1)
    list_person=cursor.fetchall()
    list_for_html={
        'list_order':list_order,
        'list_person':list_person
    }
    return render_template('manage.html',list_for_html=list_for_html)

#用户修改数据
@app.route('/user/change/<string:user>',methods=['POST','GET'])
def change(user):
    pin=request.form.get('password')
    picture=request.form.get('picture')
    if pin is not None and picture is not None:
        change = 'update person set pin="%s" where firstname=%s' % (pin, user)
        cursor.execute(change)
        change2 = 'update person set picture="%s" where firstname=%s' % (picture, user)
        cursor.execute(change2)
        dy.commit()
        return render_template('like_success.html')
    return render_template('change.html')


if __name__ == '__main__':
    app.run()
