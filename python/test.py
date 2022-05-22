import pymysql
#
#
# class MysqlHelper:
#     def __init__(self, host='127.0.0.1', post="8000", user="root", password="351172abc2015", database="ordersystem"):
#         try:
#             self.conn = pymysql.connect(host=host, user=user, password=password, database=database)
#             self.cursor = self.conn.cursor()
#         except Exception as e:
#             print(e)
#
#     def execute(self, sql):
#         self.cursor.execute(sql)
#         rowcount = self.cursor.rowcount
#         return rowcount
#
#     def delete(self, **kwargs):
#         table = kwargs.get('table')
#         where = kwargs.get('where')
#         sql = 'DELETE FROM %s where %s' % (table, where)
#         self.cursor.execute(sql)
#         try:
#             self.cursor.execute(sql)
#             self.conn.commit()
#             rowcount = self.cursor.rowcount
#         except:
#             self.conn.rollback()
#         return rowcount
#
#     def insert(self, **kwargs):
#         table = kwargs['table']
#         del kwargs['table']
#         sql = 'insert into %s(' % table
#         fields = ""
#         values = ""
#         for k, v in kwargs.items():
#             fields += "%s," % k
#             values += "'%s'," % v
#         fields = fields.rstrip(',')
#         values = values.rstrip(',')
#         sql = sql + fields + ")values(" + values + ")"
#         print(sql)
#         try:
#             self.cursor.execute(sql)
#             self.conn.commit()
#             res = self.cursor.lastrowid
#         except:
#             self.conn.rollback()
#         return res
#
#     def update(self, **kwargs):
#         table = kwargs.get('table')
#         kwargs.pop('table')
#         where = kwargs.get('where')
#         kwargs.pop('where')
#         sql = 'update %s set ' % table
#         for k, v in kwargs.items():
#             sql += '%s="%s",' % (k, v)
#         sql = sql.rstrip(',')
#         sql += ' where %s' % where
#         print(sql)
#         try:
#             self.cursor.execute(sql)
#             self.conn.commit()
#             rowcount = self.cursor.rowcount
#         except:
#             self.conn.rollback()
#         return rowcount
#
#     def selectTopone(self, **kwargs):
#         table = kwargs['table']
#         field = 'field' in kwargs and kwargs['field'] or '*'
#         where = 'where' in kwargs and 'where ' + kwargs['where'] or ''
#         order = 'order' in kwargs and 'order by ' + kwargs['order'] or ''
#         sql = 'select top 1 %s from %s %s %s ' % (field, table, where, order)
#         print(sql)
#         try:
#             self.cursor.execute(sql)
#             data = self.cursor.fetchone()
#         except:
#             self.conn.rollback()
#         return data
#
#     def selectAll(self, **kwargs):
#         table = kwargs['table']
#         field = 'field' in kwargs and kwargs['field'] or '*'
#         where = 'where' in kwargs and 'where ' + kwargs['where'] or ''
#         order = 'order' in kwargs and 'order by ' + kwargs['order'] or ''
#         sql = 'select %s from %s %s %s ' % (field, table, where, order)
#         print(sql)
#         try:
#             # 执行SQL语句
#             self.cursor.execute(sql)
#             # 使用 fetchone() 方法获取单条数据.
#             data = self.cursor.fetchall()
#         except:
#             # 发生错误时回滚
#             self.conn.rollback()
#         return data
#

py = pymysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8')
cursor = py.cursor()
cursor.execute("CREATE DATABASE IF NOT EXISTS shopsystem DEFAULT CHARACTER SET utf8")
# 进入数据库data_socket
dy = pymysql.connect(host='localhost', user='root', password='351172abc2015', port=3306, charset='utf8',
                     database='shopsystem')
cursor = dy.cursor()
# cursor.execute('CREATE TABLE IF NOT EXISTS foodmassage (id varchar(50) unique primary key ,'
#                'name varchar(50),description varchar(1000),price varchar(10),business varchar(32))')
# cursor.execute('CREATE TABLE IF NOT EXISTS passdata (firstName varchar(50) unique primary key ,'
#                'password varchar(50),identity varchar(32))')
# cursor.execute('CREATE TABLE IF NOT EXISTS business (code varchar(50) unique primary key ,'
#                'name varchar(50),description varchar(500), photo varchar(200)) ')
# conn = MysqlHelper('127.0.0.1', '3306', 'root', '351172abc2015', 'shopsystem')
name = {
    'table': 'userpass',
    'firstname': "sqt",
    'passcode': '985211',
    'identity': '1'
}

# cursor.execute('select * from passdata where firstName="sadfasfasfdsa"')
# res=cursor.fetchone()
# print("res:"+str(res))
# a = []
# print(str(a == []))
cursor.execute('CREATE TABLE IF NOT EXISTS business (code varchar(50) unique primary key ,'
               'name varchar(50),description varchar(500), photo varchar(200)) ')