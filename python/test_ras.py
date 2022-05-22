# 使用公钥加密，使用私钥解密
from Crypto import Random
from Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_v1_5 as PKCS1_cipher
import base64

random_generator = Random.new().read
rsa = RSA.generate(2048, random_generator)

# 私钥
private_key = rsa.exportKey()
with open("private_a.rsa", "wb") as f:
    f.write(private_key)
print(str(private_key))
# 公钥
public_key = rsa.publickey().exportKey()
with open("public_a.rsa", "wb") as f:
    f.write(public_key)
print(public_key)

# 使用公钥对内容进行加密


"""-----BEGIN RSA PRIVATE KEY-----
MIIEpQIBAAKCAQEAvPjKJC8WTR200rNS16JMRjPFQSN8VWsjS9I8jufoPHGt3zCB
s0LiqRyBSH7hx1yq2VDFCRjSUIQinH9Er92redbsh1Mz0s2HwdqeSQoDBN6ADyju
xF19B5lUTGbv8zv+iWQsdRVIcPuv5C4UFKSRYtSKZ9kzKXhLwN4zfpwpa91tLcTF
/dSJyVnT20sVcISwaEIZj7EyGea0xCSmkw65Ehw7O4wU2YyLkYVfeQcVWU9o+tBl
qDxRc5TLocy1q01VP4g+9JmFyGMRPlpFbFCIKwy1MoC/J+XSHXUgnjRRRugSYU0S
vXlhubHRq4pIMKC3uOHRJhrYtmLrxaSqS5+1kwIDAQABAoIBAAJ0TBJ2kW2/TjBo
XNHpIorM3v8kFK16qT2HBaX+KWbTkSReGb54HBUshhn+Goo/erE82npDmhj/8P/N
qq41YlCVnoJ5bam2D/vQDjs48pDBylxStfB98wLswQqxpuR7/yfN/G0phsR/jalt
1xvs2+iH2yOoegDMC6vTFvcffxg5Wm3+ra3WHEDdAWH5n0QcEXpxi2zmYPbIhjbi
VN+aTPQgSpVZlZiwUbHceDVzFymkiphZMvOmjz1YMKwNOgFzQVBo8Gdz0rCYl8vQ
wEcwqY+XE54iIHRNLjrY0xDkVz4XI266V9uLMdUVu6aNnxUNKG2wFVuF8usEHdos
Npkuk50CgYEAzR3aUzQaGfjC/9vsnMud5LIeWicEDAncwTwhD+6cuzM0XtbRXfSj
GkkRws6QqQLXFbJ1PDxX1G9XAE+UCreUn1P2gHxtEbZuhijzdVFOtDpF2XfP93bs
j/HWq+A5n/nQHBoS7hZ62MYCSQxlhg82KnMq8xdYPd5UHX1u8PNls6cCgYEA69ml
RUSaeozKFZ644OOpHN0ukIGmBXs26ok0CMB21krOYiQDxBiEq+2FfIIrplCk3ffD
YumbvI6Dd1XcapXns107YWprq9YEqqQmXwk62O1tUZDCv9fIH9MtmHfHbAvDcMeF
KHIk+EApBYC8GhogK0UPIL63CwoTDlujuNMi3DUCgYEAv6Vh5RWsQZH1GDwywnlY
6GIoC9GibWEVmpKEza8DT8FS8D3EVwYJErks2BhKfy+BKOuZsBMtePYiks7Hoo/O
VGON0JTxD3ilocJHXtj+MVS1CLByZmP+OawbCHIzRS6S7H7MKxpj44B2iCwF+5Dg
6h67BXBNaVj+vKMC8ixoXMcCgYEApPOXz5ZnpIaLYAueqksPoAxh4NeMUhEXG3nS
VrylYML5DVK6/49WWxjIX4h7FtsYM9ZxZOCV946FDBWVD0bAwoXWXg+cNpZ8tQwR
ei5uZgHIj197lclJQljkbtp3M24Wxu4Eh4EtsiLNjfe3l1nmXdzy86fvRV1KbqRR
IZcprnECgYEAvjqqrz0Wv3zD/qOBipJ7ZNnO1HlxIV5A0WCWgjw0EHpJxaeoohOG
BXPbYKtJ16YrVZgpqPX6FSH5YBL0Mdr8R5zqlLvh7FRME4thRzKBymmK38qRpaxf
VQIDc3gxb259f7C3F/cVUHLijAlGLCf6b7q8sgCinvIgaZziy9KXThQ=
-----END RSA PRIVATE KEY-----"""