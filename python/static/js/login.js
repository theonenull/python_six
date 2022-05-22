// 定义一个anime对象

var an=null;
let identity=document.getElementById('identity')
let firstname=document.getElementById('account')
let password=document.getElementById('password')
let box=document.getElementById('box')
let button_login=document.getElementById('button_login')
let button_register=document.getElementById('button_register')
let submit=document.getElementById('submit')
// 绑定账号输入框的点击事件
document.querySelector('#account').addEventListener('click',function(){
    if(an){
        // 如果已存在anime动画,先暂停正在运行的动画
        an.pause();
    }
    // 构造一个动画对象
    an=anime({
        targets:'path',
        strokeDashoffset:{
            value:0,
            duration:700,
            easing:'easeOutQuart'
        },
        strokeDasharray:{
            value:'240 1386',
            duration:700,
            easing:'easeOutQuart'
        }
    });
});
// 绑定密码输入框的点击事件
document.querySelector('#password').addEventListener('click',function(){
    if(an){
        // 如果已存在anime动画,先暂停正在运行的动画
        an.pause();
    }
    // 构造一个动画对象
    an=anime({
        targets:'path',
        strokeDashoffset:{
            value:-336,
            duration:700,
            easing:'easeOutQuart'
        },
        strokeDasharray:{
            value:'240 1386',
            duration:700,
            easing:'easeOutQuart'
        }
    });
});
// 绑定登录按钮的鼠标移入事件
document.querySelector('#submit').addEventListener('mouseover',function(){
    if(an){
        // 如果已存在anime动画,先暂停正在运行的动画
        an.pause();
    }
    // 构造一个动画对象
    an=anime({
        targets:'path',
        strokeDashoffset:{
            value:-730,
            duration:700,
            easing:'easeOutQuart'
        },
        strokeDasharray:{
            value:'530 1386',
            duration:700,
            easing:'easeOutQuart'
        }
    });
});

// 绑定账号输入框的获取焦点事件
document.querySelector('#account').addEventListener('focus',function(){
    if(an){
        // 如果已存在anime动画,先暂停正在运行的动画
        an.pause();
    }
    // 构造一个动画对象
    an=anime({
        targets:'path',
        strokeDashoffset:{
            value:0,
            duration:700,
            easing:'easeOutQuart'
        },
        strokeDasharray:{
            value:'240 1386',
            duration:700,
            easing:'easeOutQuart'
        }
    });
});
// 绑定密码输入框的获取焦点事件
document.querySelector('#password').addEventListener('focus',function(){
    if(an){
        // 如果已存在anime动画,先暂停正在运行的动画
        an.pause();
    }
    // 构造一个动画对象
    an=anime({
        targets:'path',
        strokeDashoffset:{
            value:-336,
            duration:700,
            easing:'easeOutQuart'
        },
        strokeDasharray:{
            value:'240 1386',
            duration:700,
            easing:'easeOutQuart'
        }
    });
});
// 绑定登录按钮的获取焦点事件
document.querySelector('#submit').addEventListener('focus',function(){
    if(an){
        // 如果已存在anime动画,先暂停正在运行的动画
        an.pause();
    }
    // 构造一个动画对象
    an=anime({
        targets:'path',
        strokeDashoffset:{
            value:-730,
            duration:700,
            easing:'easeOutQuart'
        },
        strokeDasharray:{
            value:'530 1386',
            duration:700,
            easing:'easeOutQuart'
        }
    });
});
//绑定最初的点击事件
document.querySelector('#submit').onclick=function (){
    var firstname=document.getElementById("account").value
    var password=document.getElementById("password").value
    var encryptor = new JSEncrypt()  // 创建加密对象实例
    const pubKey = "-----BEGIN PUBLIC KEY-----" +
    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
    "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
    "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
    "kTe6w/y6ii8uMQZrGwIDAQAB" +
    "-----END PUBLIC KEY-----"
    encryptor.setPublicKey(pubKey)//设置公钥
    const rsaPassWord = encryptor.encrypt(password);
    const rsaFirstName=encryptor.encrypt(firstname);
    xml=new XMLHttpRequest()
    url='http://127.0.0.1:5000/login'
    xml.open('POST',url,true)
    xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xml.send("firstname="+rsaFirstName+"&password="+rsaPassWord);
    xml.onreadystatechange=function(){
        if(xml.readyState===4 && xml.status===200)
        xml.onload=function (){
        var responseJson=JSON.parse(xml.responseText)
        if(responseJson['status']==="ALLOW"){
            if(responseJson['cookie']!=null)
            document.cookie="cookieforuser="+responseJson['cookie'].toString()
            document.cookie="userdata="+responseJson['userdata']+";max-age=604800"
            window.location="http://127.0.0.1:5000/"+responseJson['identity']
            }
        else if(responseJson['status']==="NOTALLOWED"){
            alert("输入有误！请重新输入")
        }
    }
    }

    }
//转为注册点击事件
button_login.addEventListener('click',function(){
    box.style.transition="0.5s";
    box.style.margin=0;
    submit.innerHTML="注册"
    firstname.value=""
    password.value=""
    document.querySelector('#submit').onclick=function (){
        var firstname=document.getElementById("account").value
        var password=document.getElementById("password").value

        console.log("identity:"+identity.value)
        xml=new XMLHttpRequest()
        url="http://127.0.0.1:5000/register"
        var encryptor = new JSEncrypt()  // 创建加密对象实例
//之前ssl生成的公钥，复制的时候要小心不要有空格
        const pubKey = "-----BEGIN PUBLIC KEY-----" +
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
        "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
        "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
        "kTe6w/y6ii8uMQZrGwIDAQAB" +
        "-----END PUBLIC KEY-----"
        encryptor.setPublicKey(pubKey)//设置公钥
        const rsaPassWord = encryptor.encrypt(password);
        const rsaFirstName = encryptor.encrypt(firstname);
        xml.open('POST',url,true)
        xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xml.send("firstname="+rsaFirstName+"&password="+rsaPassWord+"&identity="+identity.value);
        xml.onreadystatechange=function(){
            if(xml.readyState===4 && xml.status===200)
            xml.onload=function (){
            var json=JSON.parse(xml.responseText)
            console.log(json)
            if(json.status==="ALLOW"){
                document.cookie="cookieforuser="+json['cookie']+"; max-age=604800"
                document.cookie="userdata="+json['userdata']+"; max-age=604800"
                console.log("cookie.cookieforuser:"+document.cookie.split(";")[0].split("=")[1])
                console.log("cookie.userdata:"+document.cookie.split(";")[1].split("=")[1])
                console.log("cookie:"+document.cookie)
                console.log("xml.responseText:"+json.data)
                alert("成功注册,请记住您的密码,即将跳转到个人页面")
                window.location="http://127.0.0.1:5000/"+json['identity']
            }
            else{
                alert("用户名已被占用,重新输入")
            }

        }
        }
    }
    
})
//转为登录点击事件
button_register.addEventListener('click',function(){
    box.style.margin='0 320px';
    submit.innerHTML="登录"
    firstname.value=""
    password.value=""
    document.querySelector('#submit').onclick=function (){
    var firstname=document.getElementById("account").value
    var password=document.getElementById("password").value
    xml=new XMLHttpRequest();
    url='http://127.0.0.1:5000/login';
    xml.open('POST',url,true);
    xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    var encryptor = new JSEncrypt()  // 创建加密对象实例
    const pubKey = "-----BEGIN PUBLIC KEY-----" +
    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
    "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
    "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
    "kTe6w/y6ii8uMQZrGwIDAQAB" +
    "-----END PUBLIC KEY-----"
    encryptor.setPublicKey(pubKey)//设置公钥
    const rsaPassWord = encryptor.encrypt(password);
    const rsaFirstName=encryptor.encrypt(firstname);
    xml.send("firstname="+rsaFirstName+"&password="+rsaPassWord);
    xml.onreadystatechange=function(){
        if(xml.readyState===4 && xml.status===200)
        xml.onload=function (){
        console.log("xml.responseText:"+xml.responseText)
        var responseJson=JSON.parse(xml.responseText)
        if(responseJson['status']==="ALLOW"){
            if(responseJson['cookie']!=null)
            document.cookie="cookieforuser="+responseJson['cookie']+"; max-age=604800"
            document.cookie="userdata="+responseJson['userdata']+"; max-age=604800"
            window.location="http://127.0.0.1:5000/"+responseJson['identity']
            }
        else if(responseJson['status']==="NOTALLOWED"){
            alert("输入有误！请重新输入")
        }
        console.log("cookie:"+document.cookie)
    }
    }

    }
})
