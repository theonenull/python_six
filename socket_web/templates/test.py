import pymysql as mysql

py = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8')


# 进入数据库data_socket
dy = mysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8',
                   database='data_socket')
cursor=dy.cursor()
cursor.execute('SELECT * FROM userpass WHERE firstName="%s"' % "firstname")
print(cursor.fetchone()==None)