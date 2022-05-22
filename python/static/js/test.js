var encryptor = new JSEncrypt()  // 创建加密对象实例
//之前ssl生成的公钥，复制的时候要小心不要有空格
const pubKey = "-----BEGIN PUBLIC KEY-----" +
    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
    "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
    "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
    "kTe6w/y6ii8uMQZrGwIDAQAB" +
    "-----END PUBLIC KEY-----"
encryptor.setPublicKey(pubKey)//设置公钥
const rsaPassWord = encryptor.encrypt('avasvasvasfasdf');  // 对内容进行加密
let xml;
xml=new XMLHttpRequest()
let url;
url="http://127.0.0.1:5000/test"
xml.open('POST',url,true)
xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
console.log(rsaPassWord)
xml.send("name="+rsaPassWord);
xml.onreadystatechange=function() {
    if (xml.readyState === 4 && xml.status === 200){
        xml.onload = function () {
            // const PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCogdzMsG4S20msz32M+a1GNg2Tw4UIEGDD/dfKkoZgRtEaJtHzCXgmpP3eECHCJsK0zt0GYYxGQnfbq5mBd37xVnAlKWjVpjGQHZ+fjwn82+mRUzjmFGLs3ax79zaXJZnHTN63/yS2Rua3QY/T5Z5TLpn2YOmOn09U22eA3vdfZwIDAQAB";
            // const encrypt = new JSEncrypt();
            // encrypt.setPublicKey(PUBLIC_KEY);
            // var encrypted = encrypt.encrypt('hello');
            // console.log(typeof (encrypted))
            // console.log(encrypted);
            // var encryptor = new JSEncrypt()  // 创建加密对象实例
            // const pubKey = '-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCogdzMsG4S20msz32M+a1GNg2Tw4UIEGDD/dfKkoZgRtEaJtHzCXgmpP3eECHCJsK0zt0GYYxGQnfbq5mBd37xVnAlKWjVpjGQHZ+fjwn82+mRUzjmFGLs3ax79zaXJZnHTN63/yS2Rua3QY/T5Z5TLpn2YOmOn09U22eA3vdfZwIDAQAB-----END PUBLIC KEY-----';
            // encryptor.setPublicKey(pubKey)//设置公钥
            // const rsaPassWord = encryptor.encrypt('要加密的内容');  // 对内容进行加密
            //
        }
    }
}

function stringToByte(str) {
    var bytes = [];
    var len, c;
    len = str.length;
    for(var i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if(c >= 0x010000 && c <= 0x10FFFF) {
            bytes.push(((c >> 18) & 0x07) | 0xF0);
            bytes.push(((c >> 12) & 0x3F) | 0x80);
            bytes.push(((c >> 6) & 0x3F) | 0x80);
            bytes.push((c & 0x3F) | 0x80);
        } else if(c >= 0x000800 && c <= 0x00FFFF) {
            bytes.push(((c >> 12) & 0x0F) | 0xE0);
            bytes.push(((c >> 6) & 0x3F) | 0x80);
            bytes.push((c & 0x3F) | 0x80);
        } else if(c >= 0x000080 && c <= 0x0007FF) {
            bytes.push(((c >> 6) & 0x1F) | 0xC0);
            bytes.push((c & 0x3F) | 0x80);
        } else {
            bytes.push(c & 0xFF);
        }
    }
    return bytes;
}