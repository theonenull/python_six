// # 出现问题
SOMETHING_WRONG = '47b6954b438ecef2b0018b4048fa8a08'
// # 未付款
UNPAY = '1837c9d3eafd3b09ab90c5d537cb2ded'
// 未接单
UN_ACCEPT='a2f7beb1ceaaaf8be25b47b9f4a2487b'
// # 在路上
ON_THE_WAY = 'b83ba2819009058f3b4ababbc5ad1e6c'
// # 已经送达
ALLREADY_REACH = '606222e597ccc247d3dbd6cba33a6973'
// 已经拿到了
GET = "7c541b516c88817afcdc0256050a94ca"
let obj = {};
let cookie=document.cookie.replace(' ','')
cookie=cookie.replace(' ','')
cookie=cookie.replace('\n','')
cookie=cookie.replace('\n','')
for (let entry of cookie.split(";")) {
    let pair = entry.split("=");
    obj[pair[0].toString()] = pair[1].toString();
}
let cookieforuser=obj['cookieforuser']
let userdata=obj['userdata']
console.log(userdata)
let person_massage=document.getElementById('person_massage')
document.querySelector('#header_photo').addEventListener('mouseover',function(){
    person_massage.style.display="block"
    person_massage.setAttribute('display','block')
})
document.querySelector('#person_massage').addEventListener('mouseleave',function(){
    person_massage.style.display="none"
    person_massage.setAttribute('display','none')
})
document.querySelector('#header_text').addEventListener('mouseover',function(){
    person_massage.style.display="none"
})
document.getElementById('talkroom_massage').addEventListener('click',function (){
    window.location="http://127.0.0.1:5000/talkroom"
})
document.querySelector('#header_name').addEventListener('mouseover',function(){
    person_massage.style.display="none"
})
document.getElementById('order_list').addEventListener('click',function (){
    document.getElementById('my_order').style.display='block'
    document.getElementById('my_order').style.zIndex='10'
    let xml=new XMLHttpRequest()
    xml.open("GET","http://127.0.0.1:5000/order",true)
    xml.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xml.setRequestHeader("cookieforuser", cookieforuser);
    xml.setRequestHeader("userdata", userdata);
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4 && xml.status === 200) {
            xml.onload = function () {
                if(xml.responseText==='NO'){
                    alert('服务端出现问题')
                }
                else if (xml.responseText==='CHANGED'){
                    alert('查无此人！请勿自行修改数据')
                }
                else if(xml.responseText==='BAN'){
                    alert('禁止访问他人数据')
                }
                else {

                    let data=JSON.parse(xml.responseText)
                    console.log(data)
                    for(let i of data['order']){
                        let user_order_list_item=document.createElement('div')
                        user_order_list_item.className="user_order_list_item"
                        // 图片
                        let user_order_list_item_photo=document.createElement('div')
                        user_order_list_item_photo.className="my_order_photo user_order_list_item_photo"
                        user_order_list_item_photo.style.background="url("+i[2][5].toString()+")"
                        user_order_list_item_photo.style.backgroundSize="100% 100%"
                        user_order_list_item_photo.title="菜品图片"
                        user_order_list_item.appendChild(user_order_list_item_photo)
                        // 菜名
                        let user_order_list_item_food=document.createElement('div')
                        user_order_list_item_food.className="cursor user_order_list_text_css user_order_list_item_food"
                        user_order_list_item_food.innerText=i[2][1]
                        user_order_list_item_food.addEventListener('click',function (){
                            window.location="http://127.0.0.1:5000/food?id="+i[2][0]
                        })
                        user_order_list_item_food.title="菜品名"
                        user_order_list_item.appendChild(user_order_list_item_food)
                        //商家名
                        let user_order_list_item_business=document.createElement('div')
                        user_order_list_item_business.className="cursor user_order_list_text_css user_order_list_item_business"
                        user_order_list_item_business.innerText=i[1][1]
                        user_order_list_item_business.addEventListener('click',function (){
                            window.location="http://127.0.0.1:5000/business="+i[1][0].toString()
                        })
                        user_order_list_item_business.title="商家"
                        user_order_list_item.appendChild(user_order_list_item_business)
                        //骑手
                        let user_order_list_item_rider=document.createElement('div')
                        if(i[3] !==null){
                            user_order_list_item_rider.innerText=i[3][1]
                        }
                        user_order_list_item_rider.className="cursor user_order_list_text_css user_order_list_item_rider"
                        user_order_list_item_rider.addEventListener('click',function (){
                            window.location="http://127.0.0.1:5000/rider="+i[3][0].toString()
                        })
                        user_order_list_item_rider.title="骑手"
                        user_order_list_item.appendChild(user_order_list_item_rider)
                        //数量
                        let user_order_list_order_number=document.createElement('div')
                        user_order_list_order_number.innerText=i[0][6]
                        user_order_list_order_number.title="数量"
                        user_order_list_order_number.className="user_order_list_text_css user_order_list_item_number"
                        user_order_list_item.appendChild(user_order_list_order_number)
                        //总价
                        let user_order_list_order_all_price=document.createElement('div')
                        user_order_list_order_all_price.className="user_order_list_text_css user_order_list_item_all_price"
                        user_order_list_order_all_price.innerText=i[0][9].toString()
                        user_order_list_order_all_price.title="总价"
                        user_order_list_item.appendChild(user_order_list_order_all_price)
                        //状态
                        let user_order_list_item_statue=document.createElement('div')
                        user_order_list_item_statue.className="user_order_list_text_css user_order_list_item_statue cursor"
                        switch (i[0][5]){
                            case SOMETHING_WRONG:
                                user_order_list_item_statue.innerText="出现问题"
                                break;
                            case UNPAY:
                                user_order_list_item_statue.innerText="未付款"
                                user_order_list_item_statue.addEventListener('click',function (){
                                    const password = prompt('输入密码进行支付');
                                    if(password.toString()===''){
                                        alert('不得未空')
                                        return
                                    }
                                    var encryptor = new JSEncrypt()  // 创建加密对象实例
                                    const pubKey = "-----BEGIN PUBLIC KEY-----" +
                                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
                                    "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
                                    "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
                                    "kTe6w/y6ii8uMQZrGwIDAQAB" +
                                    "-----END PUBLIC KEY-----"
                                    let body=''
                                    encryptor.setPublicKey(pubKey)//设置公钥
                                    const rsaPassWord = encryptor.encrypt(password);
                                    body+="&password="+rsaPassWord
                                    let xml = new XMLHttpRequest()
                                    let url = 'http://127.0.0.1:5000/pay'
                                    xml.open('POST', url, true)
                                    xml.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                                    xml.setRequestHeader("cookieforuser", cookieforuser);
                                    xml.setRequestHeader('order_id',i[0][7])
                                    xml.setRequestHeader("userdata", userdata);
                                    xml.send(body)
                                    xml.onreadystatechange = function () {
                                        if (xml.readyState === 4 && xml.status === 200) {
                                            xml.onload = function () {
                                                if (xml.responseText === 'YES') {
                                                    alert('支付成功')
                                                } else {
                                                    alert('支付失败')

                                                }
                                            }
                                        }
                                    }
                                })
                                break;
                            case UN_ACCEPT:
                                user_order_list_item_statue.innerText="未接单"
                                break;
                            case ON_THE_WAY:
                                user_order_list_item_statue.innerText="在路上"
                                user_order_list_item_statue.addEventListener('click',function (){
                                   let xml=new XMLHttpRequest()
                                xml.open('POST',"http://127.0.0.1:5000/massage",true)
                                xml.setRequestHeader('cookieforuser',cookieforuser)
                                xml.setRequestHeader('userdata',userdata)
                                xml.setRequestHeader("Content-Type"
                                        , "application/x-www-form-urlencoded");
                                xml.send("massage=@"+i[3][1].toString()+"  请尽快送达")
                                xml.onreadystatechange = function () {
                                    if (xml.readyState === 4 && xml.status === 200) {
                                        if(xml.responseText==='NO'){
                                                alert('服务端出现问题')
                                            }
                                            else if (xml.responseText==='CHANGED'){
                                                alert('查无此人！请勿自行修改数据')
                                            }
                                            else if(xml.responseText==='BAN'){
                                                alert('禁止访问他人数据')
                                            }
                                            else
                                            {
                                                alert("催单成功")
                                            }

                                    }
                                }
                                })

                                break;
                            case ALLREADY_REACH:
                                user_order_list_item_statue.innerText="已经送达"
                                user_order_list_item_statue.addEventListener('click',function (){
                                    const password = prompt('输入密码进行支付');
                                    if(password.toString()===''){
                                        alert('不得未空')
                                        return
                                    }
                                    var encryptor = new JSEncrypt()  // 创建加密对象实例
                                    const pubKey = "-----BEGIN PUBLIC KEY-----" +
                                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
                                    "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
                                    "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
                                    "kTe6w/y6ii8uMQZrGwIDAQAB" +
                                    "-----END PUBLIC KEY-----"
                                    let body=''
                                    encryptor.setPublicKey(pubKey)//设置公钥
                                    const rsaPassWord = encryptor.encrypt(password);
                                    body+="&password="+rsaPassWord
                                    let xml = new XMLHttpRequest()
                                    let url = 'http://127.0.0.1:5000/get'
                                    xml.open('POST', url, true)
                                    xml.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
                                    xml.setRequestHeader("cookieforuser", cookieforuser);
                                    xml.setRequestHeader('order_id',i[0][7])
                                    xml.setRequestHeader("userdata", userdata);
                                    xml.send(body)
                                    xml.onreadystatechange = function () {
                                        if (xml.readyState === 4 && xml.status === 200) {
                                            xml.onload = function () {
                                                if (xml.responseText === 'SUCCESS') {
                                                    alert('接受成功')
                                                } else {
                                                    alert('接受失败')
                                                }
                                            }
                                        }
                                    }
                                })

                                break;
                            case GET:
                                user_order_list_item_statue.innerText="已接收"
                                break;
                        }
                        user_order_list_item_statue.title="状态"
                        user_order_list_item.appendChild(user_order_list_item_statue)
                        //时间
                        let user_order_list_item_time=document.createElement('div')
                        user_order_list_item_time.title="时间"
                        user_order_list_item_time.className="user_order_list_text_css user_order_list_item_statue"
                        user_order_list_item_time.innerText=i[0][4].toString()
                        user_order_list_item.appendChild(user_order_list_item_time)
                        //地址
                        let user_order_list_item_address=document.createElement('div')
                        user_order_list_item_address.className="user_order_list_text_css user_order_list_item_statue"
                        user_order_list_item_address.innerText=i[0][8].toString()
                        user_order_list_item_address.title="地址"
                        user_order_list_item.appendChild(user_order_list_item_address)

                        document.getElementById("user_order_list").appendChild(user_order_list_item)

                    }
                }
            }
        }
    }
})
document.getElementById('person_massage_change').addEventListener('click',function(){
    document.getElementById('add_food').style.display='block'
    document.getElementById('add_food').style.zIndex='10'
})
document.getElementById('my_order_button_exit').addEventListener('click',function (){
    document.getElementById('my_order').style.display='none'
    document.getElementById('add_food').style.display='none'
})
document.getElementById('add_food_button_exit').addEventListener('click',function (){
    document.getElementById('add_food').style.display='none'
    document.getElementById('my_order').style.display='none'
})
document.getElementById('button_to_add_address').addEventListener('click',function (){
    let address=''
    address=prompt("写下你的地址")
    console.log("new address:"+address)
    if(address==='' ||　address===null){
        alert('不能为空')
    }
    else{
        xml = new XMLHttpRequest()
        let url = 'http://127.0.0.1:5000/address'
        xml.open('POST', url, true)
        xml.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xml.setRequestHeader("cookieforuser", cookieforuser);
        xml.setRequestHeader("userdata", userdata);
        xml.send("address=" + address)
        xml.onreadystatechange = function () {
            if (xml.readyState === 4 && xml.status === 200) {
                xml.onload = function () {
                    if (xml.responseText === 'YES') {
                        alert('添加成功')
                    } else {
                        alert('未知原因，添加失败')
                    }
                }
            }
        }
    }

})
document.getElementById('add_food_button_commit').addEventListener('click',function (){
    xml=new XMLHttpRequest()
    url="http://127.0.0.1:5000/user"
    xml.open('PUT',url,true)
    xml.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xml.setRequestHeader("cookieforuser", cookieforuser);
    xml.setRequestHeader("userdata", userdata);
    var body='yes=yes'
    if(document.getElementById('userMotto').value.toString()!==''){
        body+="&user_motto="+document.getElementById('userMotto').value
    }
    if(document.getElementById('userName').value.toString()!==''){
        body+="&user_name="+document.getElementById('userName').value
    }
    if(document.getElementById('userPhoto').value.toString()!==''){
        body+="&user_photo="+document.getElementById('userPhoto').value
    }
    if(document.getElementById('userPassword').value.toString()!==''){
        alert("检测到你正在修改密码等敏感信息，请输入原先密码")
        let oldPassword=prompt("请输入一段文字","");
        let v1= md5((userdata+md5(oldPassword)).toString());
        if (cookieforuser===v1){
            console.log("V1:"+v1);
            var password=document.getElementById("userPassword").value
            var encryptor = new JSEncrypt()  // 创建加密对象实例
            const pubKey = "-----BEGIN PUBLIC KEY-----" +
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiDd/yR9lu/ZHuMBj8oLKTtZwW" +
            "v2VV3IuVrQ2u20+VwYtGaWr7zGC7ixZJaNw4CzkHvtZkh7d4LskUddimg5+mz6yL" +
            "EYtGoVzsC8WlhOspCGwZ6ajrcW0zF+3lVtD/LHzgi6rrIxwh6meVxBwUkZC9sRFM" +
            "kTe6w/y6ii8uMQZrGwIDAQAB" +
            "-----END PUBLIC KEY-----"
            encryptor.setPublicKey(pubKey)//设置公钥
            const rsaPassWord = encryptor.encrypt(password);
            body+="&password="+rsaPassWord
            console.log("返回的body："+body)
        }
        else{
            alert("验证出错，将修改除密码外的其他数据")
        }
    }
    xml.send(body)
    xml.onreadystatechange = function () {
        if (xml.readyState === 4 && xml.status === 200) {
            xml.onload = function () {
                if(xml.responseText==='NO'){
                    alert('服务端出现问题')
                }
                else if (xml.responseText==='CHANGED'){
                    alert('查无此人！请勿自行修改数据')
                }
                else if(xml.responseText==='BAN'){
                    alert('禁止访问他人数据')
                }
                else {
                    let data = JSON.parse(xml.responseText)
                    let word = ''
                    for (let i = 1; i < data['action'].toString().split('_').length; i++) {
                        if (word !== '') {
                            word += ','
                        }
                        word += data['action'].toString().split('_')[i]
                    }
                    if (data['cookie'] !== '') {
                        console.log("cookie is setting "+ data['cookie'])
                        document.cookie = "cookieforuser=" + data['cookie'] + "; max-age=604800"
                    }
                    if (word !== '') {
                        alert(data['statue'] + ',' + word + "修改成功")
                    }
                }
            }
        }
    }
})
$(document).ready(function () {
    let obj = {};
    let cookie=document.cookie.replace(' ','')
    cookie=cookie.replace('\n','')
    for (let entry of cookie.split(";")) {
        let pair = entry.split("=");
        obj[pair[0].toString()] = pair[1].toString();
    }
    const mNodelist_button = document.querySelectorAll('button');
    const mNodelist_p = document.querySelectorAll('p');
    const mNodelist_h = document.querySelectorAll('h2');
    const mNodelist_div=document.querySelectorAll('.card')
    let xml = new XMLHttpRequest()
    url = 'http://127.0.0.1:5000/preview'
    xml.open('GET', url)
    xml.setRequestHeader("cookieforuser", cookieforuser);
    xml.setRequestHeader("userdata",userdata);
    xml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xml.send();
    xml.onreadystatechange = function () {
        if (xml.readyState === 4 && xml.status === 200) {
            xml.onload = function () {
                    if(xml.responseText==='NO'){
                        alert('服务端出现问题')
                    }
                    else if (xml.responseText==='CHANGED'){
                        alert('查无此人！请勿自行修改数据')
                    }
                    else if(xml.responseText==='BAN'){
                        alert('禁止访问他人数据')
                    }
                    else{
                        let json = JSON.parse(xml.responseText)
                        document.getElementById('header_name').innerText=json['user_name']
                        document.getElementById('header_photo').style.background='url('+json['user_photo'].toString()+')'
                        document.getElementById('header_photo').style.backgroundSize="100% 100%"
                        console.log("xml.responseText:" + xml.responseText)
                        for (let i = 0; i < 10; i++) {
                            console.log(i)
                            let id=json['food'][i][0]
                            console.log("add_click: "+id)
                            console.log("url: "+'http://127.0.0.1:5000/foodpage?id=' + id)
                            mNodelist_button[i].addEventListener("click", function () {
                                window.location = 'http://127.0.0.1:5000/foodpage?id=' + id
                            })
                            mNodelist_p[i].innerText = json['food'][i][2]
                            mNodelist_h[i].innerText = json['food'][i][1]
                            console.log("image:"+json['food'][2].toString())
                            mNodelist_div[i].style.background='url('+json['food'][i][5].toString()+')'
                            mNodelist_div[i].style.backgroundSize="100% 100%"
                        }
                        for (let j = 0; j < 10; j++) {
                            mNodelist_button[j + 10].addEventListener("click", function () {
                                window.location = 'http://127.0.0.1:5000/business?id=' + json['business'][j][0]
                            })
                            mNodelist_h[j + 10].innerText = json['business'][j][0];
                            mNodelist_p[j + 10].innerText = json['business'][j][1];
                    }
                }
            }
        }
    }
})




