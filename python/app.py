import hashlib
from datetime import timedelta, datetime
import base64
from time import time
import threading
from flask_socketio import SocketIO, emit
from Crypto import Random
from Crypto.Cipher import PKCS1_v1_5 as Cipher_pkcs1_v1_5
from Crypto.PublicKey import RSA
from flask import Flask, render_template, request, jsonify, session, abort
import pymysql

from key import RANDOM_GENERATOR

talk_member = []
print('成员:' + str(talk_member))
# 出现问题
SOMETHING_WRONG = '47b6954b438ecef2b0018b4048fa8a08'
# 未付款
UNPAY = '1837c9d3eafd3b09ab90c5d537cb2ded'
# 未接单
UN_ACCEPT = 'a2f7beb1ceaaaf8be25b47b9f4a2487b'
# 在路上
ON_THE_WAY = 'b83ba2819009058f3b4ababbc5ad1e6c'
# 已经送达
ALLREADY_REACH = '606222e597ccc247d3dbd6cba33a6973'
# 已拿到
GET = "7c541b516c88817afcdc0256050a94ca"

USER = 'c4ca4238a0b923820dcc509a6f75849b'
BUSINESS = 'c81e728d9d4c2f636f067f89cc14862c'
RIDER = "eccbc87e4b5ce2fe28308fd9f2a7baf3"
MANAGER = 'a87ff679a2f3e71d9181a67b7542122c'
identityList = {
    USER: 'user',
    BUSINESS: 'business',
    RIDER: 'rider',
    MANAGER: 'manage'

}
from Crypto import Random
from Crypto.PublicKey import RSA
from flask import jsonify


# md5加密方法
def encrypt(string):
    h1 = hashlib.md5()
    h1.update(string.encode('utf-8'))
    return h1.hexdigest()


# mysql操作类
class MysqlHelper:
    def __init__(self, host, port, user, password, database):
        try:
            self.conn = pymysql.connect(host=host, user=user, port=port, password=password, database=database)
            self.cursor = self.conn.cursor()
        except Exception as e:
            print("here")
            print(e)

    def execute(self, sql):
        self.cursor.execute(sql)
        rowcount = self.cursor.rowcount
        return rowcount

    def delete(self, **kwargs):
        table = kwargs.get('table')
        where = kwargs.get('where')
        sql = 'DELETE FROM %s where %s' % (table, where)
        self.cursor.execute(sql)
        try:
            self.cursor.execute(sql)
            self.conn.commit()
            rowcount = self.cursor.rowcount
        except Exception as e:
            print(e)
            self.conn.rollback()
        return rowcount

    def insert(self, **kwargs):
        table = kwargs['table']
        del kwargs['table']
        sql = 'insert into %s(' % table
        fields = ""
        values = ""
        for k, v in kwargs.items():
            fields += "%s," % k
            values += "'%s'," % v
        fields = fields.rstrip(',')
        values = values.rstrip(',')
        sql = sql + fields + ")values(" + values + ")"
        print(sql)
        try:
            self.cursor.execute(sql)
            self.conn.commit()
            res = self.cursor.lastrowid
            print("insert right")
            return res
        except Exception as e:
            print(e)
            self.conn.rollback()
            print("insert bug")
            return -1

    def update(self, **kwargs):
        table = kwargs.get('table')
        kwargs.pop('table')
        where = kwargs.get('where')
        kwargs.pop('where')
        sql = 'update %s set ' % table
        for k, v in kwargs.items():
            sql += '%s="%s",' % (k, v)
        sql = sql.rstrip(',')
        sql += ' where %s' % where
        print(sql)
        try:
            self.cursor.execute(sql)
            self.conn.commit()
            rowcount = self.cursor.rowcount
        except Exception as e:
            print(e)
            self.conn.rollback()
            rowcount = 0
        return rowcount

    def selectTopone(self, **kwargs):
        table = kwargs['table']
        where = 'where' in kwargs and 'where ' + kwargs['where'] or ''
        order = 'order' in kwargs and 'order by ' + kwargs['order'] or ''
        sql = 'select * from %s %s %s ' % (table, where, order)
        print(sql)
        try:
            self.cursor.execute(sql)
            data = self.cursor.fetchone()
            print('select one here:' + str(data))
            return data
        except Exception as e:
            print(e)
            self.conn.rollback()
            return None

    def selectAll(self, **kwargs):
        table = kwargs['table']
        field = 'field' in kwargs and kwargs['field'] or '*'
        where = 'where' in kwargs and 'where ' + kwargs['where'] or ''
        order = 'order' in kwargs and 'order by ' + kwargs['order'] or ''
        limit = 'limit' in kwargs and 'limit ' + kwargs['limit'] or ''
        sql = 'select %s from %s %s %s %s' % (field, table, where, order, limit)
        print(sql)
        try:
            self.cursor.execute(sql)
            data = self.cursor.fetchall()
        except Exception as e:
            print(e)
            self.conn.rollback()
        return data

    def updateDATA(self, **kwargs):
        table = kwargs['table']
        where = 'where' in kwargs and 'where ' + kwargs['where'] or ''
        set = 'set' in kwargs and 'set ' + kwargs['set']
        # UPDATE
        # table_name
        # SET
        # field1 = new - value1, field2 = new - value2
        sql = 'UPDATE %s %s %s' % (table, set, where)
        try:
            self.cursor.execute(sql)
            return "YES"
        except Exception as e:
            print(e)
            self.conn.rollback()
            return "NO"


# 身份验证 cookie
def verify(userdata, cookie):
    verificationData = conn.selectTopone(table='passdata', where='firstname="%s"' % userdata)
    if verificationData is not None:
        if encrypt(verificationData[0] + verificationData[1]) != cookie:
            return 'BAN'
        else:
            return 'PASS'
    else:
        return 'CHANGED'


def password_verify(rsa_password, uerdata, cookie):
    print("rsa_password:" + str(rsa_password))
    print("rsa_password:" + str(request.form.get('yes')))
    print("------1-----------")
    if rsa_password is not None:
        password_ls = rsa_password.replace(" ", "+", 200)
        with open('master-private.pem') as f:
            key = f.read()
        rsakey = RSA.importKey(key)
        cipher = Cipher_pkcs1_v1_5.new(rsakey)
        print(password_ls)
        try:
            print("--------2---------")
            password = cipher.decrypt(base64.b64decode(password_ls), RANDOM_GENERATOR).decode()
            print("登录解密后的密码：" + password)
            password_mysql = encrypt(password)
            if cookie == encrypt(uerdata + password_mysql):
                return "YES"
            else:
                return "NO"
        except Exception as e:
            print(e)
            print("警告！数据已被篡改")
            return "NO"


app = Flask(__name__)
conn = MysqlHelper('127.0.0.1', 3306, 'root', '351172abc2015', 'shopsystem')
app.config['SECRET_KEY'] = 'secret_key'
app.config['PERMANENT_SESSION_LIFETIME'] = timedelta(days=7)
socketio = SocketIO()
socketio.init_app(app, cors_allowed_origins='*')


@app.route('/')
def hello_world():
    return render_template('login.html')


# 登录验证
@app.route('/login', methods=['POST'])
def login():
    rsa_firstname = request.form.get('firstname')
    rsa_password = request.form.get('password')
    password_ls = rsa_password.replace(" ", "+", 200)
    firstname_ls = rsa_firstname.replace(" ", "+", 200)
    with open('master-private.pem') as f:
        key = f.read()
    rsakey = RSA.importKey(key)
    cipher = Cipher_pkcs1_v1_5.new(rsakey)
    print(password_ls)
    print(firstname_ls)
    try:
        password = cipher.decrypt(base64.b64decode(password_ls), RANDOM_GENERATOR).decode()
        firstname = cipher.decrypt(base64.b64decode(firstname_ls), RANDOM_GENERATOR).decode()
        print("登录解密后的密码：" + password)
        print("登录解密后的用户名：" + firstname)
    except Exception as e:
        print("警告！数据已被篡改")
    firstname_mysql = encrypt(firstname)
    password_mysql = encrypt(password)
    result = conn.selectTopone(table='passdata', where='firstname="%s"' % firstname_mysql)
    if result is not None:
        if result[1] == password_mysql:
            cookie = encrypt(firstname_mysql + password_mysql)
            userdata = firstname_mysql
            data = {
                "status": "ALLOW",
                "cookie": cookie,
                "userdata": userdata,
                "identity": identityList.get(result[2])
            }
            print("登录返回：" + str(data))
            return jsonify(data)
        else:
            data = {
                "status": "NOTALLOWED"
            }
            return jsonify(data)
    else:
        data = {
            "status": "NOTALLOWED"
        }
        return jsonify(data)


# 用户注册
@app.route('/register', methods=['POST'])
def register():
    rsa_firstname = request.form.get('firstname')
    rsa_password = request.form.get('password')
    password_ls = rsa_password.replace(" ", "+", 200)
    firstname_ls = rsa_firstname.replace(" ", "+", 200)
    with open('master-private.pem') as f:
        key = f.read()
    rsakey = RSA.importKey(key)
    cipher = Cipher_pkcs1_v1_5.new(rsakey)
    print(password_ls)
    print(firstname_ls)
    try:
        password = cipher.decrypt(base64.b64decode(password_ls), RANDOM_GENERATOR).decode()
        firstname = cipher.decrypt(base64.b64decode(firstname_ls), RANDOM_GENERATOR).decode()
        print("注册解密后的密码" + password)
        print("注册解密后的用户名" + firstname)
    except Exception as e:
        print("数据已被篡改")
    identity = request.form.get('identity')
    firstname_mysql = encrypt(str(firstname))
    password_mysql = encrypt(str(password))
    result = conn.selectTopone(table='passdata', where='firstName="%s"' % firstname_mysql)
    if result is not None:
        data = {
            'data': "ALREADY"
        }
        return jsonify(data)
    else:
        cookie = encrypt(firstname_mysql + password_mysql)
        userdata = firstname_mysql
        if identity == '0':
            identity_mysql = USER
            conn.insert(table='user', code=firstname_mysql, name="默认名字",
                        photo="http://127.0.0.1:5000/static/image/start.png", motto="写下你的人生态度")
        elif identity == '1':
            identity_mysql = BUSINESS
            conn.insert(table='business', code=firstname_mysql, name="默认名字",
                        photo="http://127.0.0.1:5000/static/image/start.png")
            conn.insert(table='business_criticism', code=firstname_mysql, criticism='0')
            conn.insert(table='business_income', code=firstname_mysql, income='0')
            conn.insert(table='business_favor', code=firstname_mysql, favor='0')
            conn.insert(table='business_sale', code=firstname_mysql, sale='0')
        elif identity == '2':
            identity_mysql = RIDER
            conn.insert(table='rider', code=firstname_mysql, name="默认名字",
                        photo="http://127.0.0.1:5000/static/image/start.png")
            conn.insert(table='rider_criticism', code=firstname_mysql, criticism='0')
            conn.insert(table='rider_income', code=firstname_mysql, income='0')
            conn.insert(table='rider_favor', code=firstname_mysql, favor='0')
            conn.insert(table='rider_send', code=firstname_mysql, send='0')
        else:
            identity_mysql = MANAGER
        conn.insert(table='passdata', firstName=firstname_mysql, password=password_mysql, identity=identity_mysql)
        data = {
            "status": "ALLOW",
            "cookie": cookie,
            "userdata": userdata,
            "identity": identityList.get(identity_mysql)
        }
        return jsonify(data)


# 获取菜品页面
@app.route('/foodpage', methods=['GET'])
def getFoodPage():
    id = request.args.get('id')
    print("id is " + id)
    data = {
        'id': id
    }
    return render_template('food.html', data=data)


# 添加订单
@app.route('/order', methods=['POST'])
def add_order():
    success = "True"
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        print(verify(userdata, cookie))
        return verify(userdata, cookie)
    try:
        business_id = request.form.get('business_id')
        user_id = request.form.get('user_id')
        food_id = request.form.get('food_id')
        order_time = datetime.now().strftime('%Y-%m-%d %I:%M:%S %p')
        order_id = encrypt(business_id + user_id + food_id + str(time()))
        food_number = request.form.get('food_number')
        all_price_number = request.form.get('all_price_number')
        address = request.form.get('address')
        if conn.insert(table="food_order", business_id=business_id, user_id=user_id, food_id=food_id,
                       order_time=order_time,
                       order_statue=UNPAY, order_number=food_number, order_id=order_id, order_address=address,
                       order_transactions=all_price_number) == -1:
            ex = Exception("insert bug")
            raise ex
    except Exception as e:
        print(e)
        return "NO"
    return "YES"


# 获取订单
@app.route('/order', methods=['GET'])
def getOrderList():
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    try:
        order_list_for_return = []
        order_list_mysql = conn.selectAll(table='food_order', where='user_id="%s"' % userdata)
        for i in order_list_mysql:
            temp = []
            temp.append(i)
            businessDataFromMysql = conn.selectTopone(table='business', where='code="%s"' % i[0])
            temp.append(businessDataFromMysql)
            foodDataFromMysql = conn.selectTopone(table='foodmassage', where='id="%s"' % i[3])
            temp.append(foodDataFromMysql)
            riderDataFromMysql = conn.selectTopone(table='rider', where='code="%s"' % i[2])
            temp.append(riderDataFromMysql)
            order_list_for_return.append(temp)
        print(order_list_for_return)
        data = {
            'order': order_list_for_return
        }
        return jsonify(data)
    except Exception as e:
        print(e)
        return "NO"


# 获取菜品信息
@app.route('/food', methods=['GET'])
def getFoodData():
    print("---------------getFoodData--------------------")
    id = request.args.get('id')
    print("id: " + id)
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("COOKIE: " + cookie)
    print("USERDATA: " + userdata)
    result = verify(userdata, cookie)
    if result != 'PASS':
        return result
    foodMassage = conn.selectTopone(table='foodmassage', where='id="%s"' % id)
    business_name = conn.selectTopone(table='business', where='code="%s"' % foodMassage[4])[1]
    address = []
    for i in conn.selectAll(table="address", field="address", where='id="%s"' % userdata):
        print(str(i[0]))
        address.append(str(i[0]))
    data = {
        'food_id': id,
        'food_name': foodMassage[1],
        'food_description': foodMassage[2],
        'food_price': foodMassage[3],
        'food_business': foodMassage[4],
        'food_image': foodMassage[5],
        'food_business_name': business_name,
        'address': address
    }
    return jsonify(data)


# 用户推荐主页 数据
@app.route('/preview', methods=['GET'])
def homepage():
    if request.method == 'GET':
        cookie = request.headers.get('cookieforuser')
        userdata = request.headers.get('userdata')
        print("cookie: " + cookie + " userdata" + userdata)
        if verify(userdata, cookie) != "PASS":
            return verify(userdata, cookie)
        result = conn.selectAll(table='foodmassage', field='*', limit='10')
        userData = conn.selectTopone(table='user', where='code="%s"' % userdata)
        print("userdata:" + str(userData))
        data = {
            'food': result,
            'business': (
                ('a', '2'), ('s', '2'), ('d', '2'), ('f', '2'), ('g', '2'), ('h', '2'), ('j', '2'), ('k', '2'),
                ('l', '2'), (';', '2')),
            'user_name': userData[1],
            'user_photo': userData[2],
            'user_motto': userData[3]
        }
        print('寻找bug:' + str(data))
        return jsonify(data)


# 用户推荐主页
@app.route('/user', methods=['GET'])
def getPrePage():
    return render_template('user.html')


# 用户修改信息
@app.route('/user', methods=['PUT'])
def changeUserData():
    print('找bug：')
    action = 'action'
    cookieForReturn = ''
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata: " + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    try:
        user_name = request.form.get('user_name')
        print('user_name:' + str(user_name))
        if user_name is not None:
            conn.update(table="user", name=user_name, where='code = "%s"' % userdata)
            action += '_用户名'
        user_photo = request.form.get('user_photo')
        print('user_photo:' + str(user_photo) + '  ' + str(type(user_photo)))
        if user_photo is not None:
            print('name++++++++++++++')
            conn.update(table="user", photo=user_photo, where='code = "%s"' % userdata)
            action += '_头像'
        user_motto = request.form.get('user_motto')
        print('user_motto:' + str(user_motto) + str(type(user_motto)))
        if user_motto is not None:
            conn.update(table="user", motto=user_motto, where='code = "%s"' % userdata)
            action += '_个人签名'
        rsa_password = request.form.get('password')
        print("rsa_password:" + str(rsa_password))
        print("rsa_password:" + str(request.form.get('yes')))
        print("------1-----------")
        if rsa_password is not None:
            password_ls = rsa_password.replace(" ", "+", 200)
            with open('master-private.pem') as f:
                key = f.read()
            rsakey = RSA.importKey(key)
            cipher = Cipher_pkcs1_v1_5.new(rsakey)
            print(password_ls)
            try:
                print("--------2---------")
                password = cipher.decrypt(base64.b64decode(password_ls), RANDOM_GENERATOR).decode()
                print("登录解密后的密码：" + password)
                password_mysql = encrypt(password)
                cookieForReturn = encrypt(userdata + password_mysql)
                userdata = userdata
                conn.update(table="passdata", where='firstname="%s"' % userdata, password=password_mysql)
                action += '_密码'
            except Exception as e:
                print(e)
                print("警告！数据已被篡改")
            data = {
                "cookie": cookieForReturn,
                "action": action,
                "statue": "执行成功"
            }
            return jsonify(data)
        data = {
            "cookie": cookieForReturn,
            "action": action,
            "statue": "执行成功"
        }
        return jsonify(data)
    except Exception as e:
        print("错误原因：" + str(e))
        data = {
            "action": action,
            "statue": "错误",
            "cookie": cookieForReturn
        }
        return jsonify(data)


# 支付
@app.route('/pay', methods=['POST'])
def pay():
    cookie = request.headers.get("cookieforuser")
    userdata = request.headers.get('userdata')
    order = request.headers.get('order_id')
    rsa_password = request.form.get('password')
    password_ls = rsa_password.replace(" ", "+", 200)
    with open('master-private.pem') as f:
        key = f.read()
    rsakey = RSA.importKey(key)
    cipher = Cipher_pkcs1_v1_5.new(rsakey)
    print(password_ls)
    try:
        password = cipher.decrypt(base64.b64decode(password_ls), RANDOM_GENERATOR).decode()
        print("登录解密后的密码：" + password)
    except Exception as e:
        print("警告！数据已被篡改")
    if encrypt(userdata + encrypt(password)) != cookie:
        return 'NO'
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    else:
        try:
            conn.update(table='food_order', where='order_id="%s"' % order, order_statue=UN_ACCEPT)
            return "YES"
        except Exception as e:
            print(e)
            return "NO"


# 添加地址
@app.route('/address', methods=['POST'])
def addAddress():
    cookie = request.headers.get("cookieforuser")
    userdata = request.headers.get('userdata')
    address = request.form.get('address')
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    else:
        try:
            conn.insert(table='address', id=userdata, address=address)
            return "YES"
        except Exception as e:
            print(e)
            return "NO"


# 商家页面
@app.route('/business', methods=['GET', 'POST'])
def getBusinessData():
    print("----------------business----------------")
    if request.method == 'POST':
        cookie = request.form.get("cookieforuser")
        userdata = request.form.get('userdata')
        if verify(userdata, cookie) != "PASS":
            return verify(userdata, cookie)
        print("cookie:" + cookie)
        print("userdata:" + userdata)
        businessData = conn.selectTopone(table='business', where='code="%s"' % userdata)
        businessData_income = conn.selectTopone(table='business_income', where='code="%s"' % userdata)
        businessData_sale = conn.selectTopone(table='business_sale', where='code="%s"' % userdata)
        businessData_criticism = conn.selectTopone(table='business_criticism', where='code="%s"' % userdata)
        businessData_favor = conn.selectTopone(table='business_favor', where='code="%s"' % userdata)
        businessData_food = conn.selectAll(table='foodmassage', where='business="%s"' % userdata)
        businessData_food_length = len(businessData_food)
        print("businessData:" + str(businessData))
        try:
            order_list_for_return = []
            order_list_mysql = conn.selectAll(table='food_order', where='business_id="%s"' % userdata)
            print('business_order 订单:' + str(order_list_mysql))
            for i in order_list_mysql:
                temp = []
                temp.append(i)
                businessDataFromMysql = conn.selectTopone(table='user', where='code="%s"' % i[1])
                temp.append(businessDataFromMysql)
                foodDataFromMysql = conn.selectTopone(table='foodmassage', where='id="%s"' % i[3])
                temp.append(foodDataFromMysql)
                order_list_for_return.append(temp)
                print(order_list_for_return)
            data = {
                "name": businessData[1],
                "description": businessData[2],
                "photo": businessData[3],
                "sale": businessData_sale[1],
                "income": businessData_income[1],
                "criticism": businessData_criticism[1],
                "favor": businessData_favor[1],
                "food": businessData_food,
                "length": businessData_food_length,
                "order": order_list_for_return
            }
            return jsonify(data)
        except Exception as e:
            print(e)
            return "NO"
    if request.method == 'GET':
        return render_template('business.html')


# 添加菜品
@app.route('/food/addition', methods=['POST'])
def add_food():
    print("---------------add_food--------------------")
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    food_price = request.form.get('food_price')
    food_name = request.form.get('food_name')
    food_describe = request.form.get('food_describe')
    food_image = request.form.get('food_image')
    print("添加菜品信息" + str(food_name) + "  " + str(food_price) + "  " + str(food_describe) + "  " + str(food_image))
    try:
        code = encrypt(food_image + food_name + str(time))
        conn.insert(table='foodMassage', id=code, business=userdata, image=food_image, description=food_describe,
                    price=food_price, name=food_name)

    except Exception as e:
        print(e)
        return "NO"
    print("---------------add_food--------------------")
    return "PASS"


@app.route('/talkroom')
def talkroom():
    return render_template('talk.html')


@app.route('/rider')
def getRiderData():
    return render_template('rider.html')


@app.route('/ridermassage')
def getRiderMassage():
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    riderData = conn.selectTopone(table='rider', where='code="%s"' % userdata)
    riderData_income = conn.selectTopone(table='rider_income', where='code="%s"' % userdata)
    riderData_send = conn.selectTopone(table='rider_send', where='code="%s"' % userdata)
    riderData_criticism = conn.selectTopone(table='rider_criticism', where='code="%s"' % userdata)
    riderData_favor = conn.selectTopone(table='rider_favor', where='code="%s"' % userdata)
    try:
        order_list_for_select = []
        order_list_mysql2 = conn.selectAll(table='food_order',
                                           where='order_statue="%s"' % UN_ACCEPT)
        for i in order_list_mysql2:
            temp = []
            temp.append(i)
            userDataFromMysql = conn.selectTopone(table='user', where='code="%s"' % i[1])
            temp.append(userDataFromMysql)
            foodDataFromMysql = conn.selectTopone(table='foodmassage', where='id="%s"' % i[3])
            temp.append(foodDataFromMysql)
            businessDataFromMysql = conn.selectTopone(table='business', where='code="%s"' % i[0])
            temp.append(businessDataFromMysql)
            order_list_for_select.append(temp)
        order_list_for_send = []
        order_list_mysql = conn.selectAll(table='food_order',
                                          where='rider_id="%s" and order_statue="%s"' % (userdata, ON_THE_WAY))
        for i in order_list_mysql:
            temp = []
            temp.append(i)
            userDataFromMysql = conn.selectTopone(table='user', where='code="%s"' % i[1])
            temp.append(userDataFromMysql)
            foodDataFromMysql = conn.selectTopone(table='foodmassage', where='id="%s"' % i[3])
            temp.append(foodDataFromMysql)
            businessDataFromMysql = conn.selectTopone(table='business', where='code="%s"' % i[0])
            temp.append(businessDataFromMysql)
            order_list_for_send.append(temp)
        data = {
            "name": riderData[1],
            "motto": riderData[3],
            "photo": riderData[2],
            "send": riderData_send[1],
            "income": riderData_income[1],
            "criticism": riderData_criticism[1],
            "favor": riderData_favor[1],
            "order_for_select": order_list_for_select,
            "order_for_send": order_list_for_send,
        }
        return jsonify(data)
    except Exception as e:
        print(e)
        return "NO"


@app.route('/get',methods=['POST'])
def user_get():
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    ras_password = request.form.get('password')
    if password_verify(ras_password, userdata, cookie) == 'YES':
        try:
            order_id = request.headers.get('order_id')
            conn.update(table='food_order', where='order_id="%s"' % order_id,
                        order_statue=GET)
            return "SUCCESS"
        except Exception as e:
            print(e)
            return "FAIL"


@app.route('/accept', methods=['POST'])
def accept_order():
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    try:
        order_id = request.form.get('order_id')
        conn.update(table='food_order', where='order_id="%s"' % order_id, rider_id=userdata, order_statue=ON_THE_WAY)
        return "SUCCESS"
    except Exception as e:
        print(e)
        return 'FAIL'


@app.route('/send', methods=['POST'])
def send_order():
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    try:
        order_id = request.form.get('order_id')
        conn.update(table='food_order', where='order_id="%s"' % order_id, rider_id=userdata,
                    order_statue=ALLREADY_REACH)
        return "SUCCESS"
    except Exception as e:
        print(e)
        return 'FAIL'


@app.route('/test', methods=['GET', 'POST'])
def test():
    if request.method == 'GET':
        return render_template('test.html')
    else:
        name = request.form.get('name')
        print(name)
        password = name.replace(" ", "+", 200)
        print(password)
        with open('master-private.pem') as f:
            key = f.read()
        rsakey = RSA.importKey(key)
        cipher = Cipher_pkcs1_v1_5.new(rsakey)
        password = cipher.decrypt(base64.b64decode(password), RANDOM_GENERATOR)
        # 如果返回的password类型不是str，说明秘钥公钥不一致，或者程序错误
        print("111" + password.decode())
        return '0'


# 聊天室
name_space = '/talkRoomData'
data = {
    'massage': "str(massage)"
}


def emitMassage(data):
    socketio.emit('in', data=data, namespace=name_space)
    return "yes"


@app.route('/massage', methods=['POST'])
def getMassage():
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    print("cookie: " + cookie + " userdata" + userdata)
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    massage = request.form.get('massage')
    result = conn.selectTopone(table='passdata', where='firstname="%s"' % userdata)
    identity = ''
    name = ''
    photo = ''
    if result[2] == BUSINESS:
        identity = "商家"
        name = conn.selectTopone(table='business', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='business', where='code="%s"' % userdata)[3]
    elif result[2] == RIDER:
        identity = "骑手"
        name = conn.selectTopone(table='rider', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='rider', where='code="%s"' % userdata)[2]
        print("bug---------"+photo)
    else:
        identity = "用户"
        name = conn.selectTopone(table='user', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='user', where='code="%s"' % userdata)[2]
    print("massage:" + str(massage))
    time = datetime.now().strftime('%Y-%m-%d %I:%M:%S %p')
    socketio.emit('in', data={
        'massage': str(massage),
        'name': name,
        'identity': identity,
        'photo': photo,
        'time': time
    }, namespace=name_space)
    conn.insert(table='talk_massage', massage=str(massage), name=name, identity=identity,
                photo=photo, time=time, userdata=userdata)
    return "yes"


def getPersonMassageForTalk(userdata):
    result = conn.selectTopone(table='passdata', where='firstname="%s"' % userdata)
    identity = ''
    name = ''
    photo = ''
    if result[2] == BUSINESS:
        identity = "商家"
        name = conn.selectTopone(table='business', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='business', where='code="%s"' % userdata)[3]
    elif result[2] == RIDER:
        identity = "骑手"
        name = conn.selectTopone(table='rider', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='rider', where='code="%s"' % userdata)[3]
    else:
        name = conn.selectTopone(table='user', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='user', where='code="%s"' % userdata)[3]
    data = {
        'massage': conn.selectAll(table='talk_massage'),
        'member': talk_member,
        'photo': photo,
        'name': name
    }
    return jsonify(data)


@app.route('/massage', methods=['GET'])
def getOldMassage():
    cookie = request.headers.get('cookieforuser')
    userdata = request.headers.get('userdata')
    if verify(userdata, cookie) != "PASS":
        return verify(userdata, cookie)
    result = conn.selectTopone(table='passdata', where='firstname="%s"' % userdata)
    if result[2] == BUSINESS:
        identity = "商家"
        name = conn.selectTopone(table='business', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='business', where='code="%s"' % userdata)[3]
    elif result[2] == RIDER:
        identity = "骑手"
        name = conn.selectTopone(table='rider', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='rider', where='code="%s"' % userdata)[2]
        print("bug---------"+photo)
    else:
        name = conn.selectTopone(table='user', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='user', where='code="%s"' % userdata)[2]
    print("bug---------" + photo)
    data = {
        'massage': conn.selectAll(table='talk_massage'),
        'member': talk_member,
        'photo': photo,
        'name': name
    }
    return jsonify(data)


def connect(userdata):
    print('userdata=' + str(userdata))
    print('talk_member:' + str(talk_member))
    result = conn.selectTopone(table='passdata', where='firstname="%s"' % userdata)
    identity = ''
    name = ''
    photo = ''
    if result[2] == BUSINESS:
        identity = "商家"
        name = conn.selectTopone(table='business', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='business', where='code="%s"' % userdata)[3]
    elif result[2] == RIDER:
        identity = "骑手"
        name = conn.selectTopone(table='rider', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='rider', where='code="%s"' % userdata)[3]
    else:
        name = conn.selectTopone(table='user', where='code="%s"' % userdata)[1]
        photo = conn.selectTopone(table='user', where='code="%s"' % userdata)[3]
    if talk_member.count([userdata, name]) == 0:
        talk_member.append([userdata, name])
        socketio.emit('join_member', data={'name': name, 'identity': identity, 'member': talk_member},
                      namespace=name_space)
        print('成员:' + str(talk_member))
        print('client connected.')


@socketio.on('connect', namespace=name_space)
def connected_msg():
    userdata = request.args.get('userdata')
    t1 = threading.Thread(target=connect, args=(userdata,))
    t1.start()


def connected_msg(userdata):
    print('userdata=' + str(userdata))
    if talk_member.count(userdata) == 1:
        print('client disconnected.')
    else:
        print('dis_userdata=' + str(userdata))
        for i in talk_member:
            if i[0] == userdata:
                talk_member.remove(i)
        print('talk_member:' + str(talk_member))
        socketio.emit('join_member', data={'member': talk_member},
                      namespace=name_space)
        print("断开")
        return None


@socketio.on('disconnect', namespace=name_space)
def start_disconnect():
    userdata = request.args.get('userdata')
    t1 = threading.Thread(target=connected_msg, args=(userdata,))
    t1.start()


if __name__ == '__main__':
    socketio.run(app, host='0.0.0.0', port=5000, debug=True)
