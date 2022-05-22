
# -*- encoding:utf-8 -*-
from Crypto.PublicKey import RSA
from flask import current_app
from Crypto import Random
# rsa算法生成实例
RANDOM_GENERATOR=Random.new().read
if __name__=='__main__':
    rsa = RSA.generate(1024, RANDOM_GENERATOR)
    # master的秘钥对的生成
    PRIVATE_PEM = rsa.exportKey()
    with open('master-private.pem', 'wb') as f:
        f.write(PRIVATE_PEM)

    PUBLIC_PEM = rsa.publickey().exportKey()

    with open('master-public.pem', 'wb') as f:
        f.write(PUBLIC_PEM)
        print(str(PUBLIC_PEM))