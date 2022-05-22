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
let food_photo=document.getElementById('food_photo')
let food_name=document.getElementById('food_name')
let food_business_name=document.getElementById('food_business_name')
let food_describe=document.getElementById('food_describe')
let button_decrease=document.getElementById('button_decrease')
let food_number=document.getElementById('food_number')
let button_add=document.getElementById('button_add')
let address_text=document.getElementById('address_text')
let address_select=document.getElementById('address_select')
let food_singe_price=document.getElementById('single_price_number')
let all_price_number=document.getElementById('all_price_number')
let button_buy=document.getElementById('button_buy')
console.log(food_name.getAttribute('food_id'))
button_add.addEventListener('click',function (){
    if(food_number.innerText==="20"){
        alert('wow,好有钱')
    }
    food_number.innerText=(parseInt(food_number.innerText.toString())+1).toString()
    all_price_number.innerText=(parseFloat(food_singe_price.innerText.toString())*(parseInt(food_number.innerText.toString()))).toString()
})
button_decrease.addEventListener('click',function (){
    if(food_number.innerText==='0'){
        alert('无法再减小了')
    }
    else{
        food_number.innerText=(parseInt(food_number.innerText.toString())-1).toString()
        all_price_number.innerText=(parseFloat(food_singe_price.innerText.toString())*(parseInt(food_number.innerText.toString()))).toString()

    }
})
button_buy.addEventListener('click',function (){

    if (food_number.innerText === '0') {
        alert("不得为空")
    } else {
        let body = ''
        let xml = new XMLHttpRequest()
        xml.open('POST', 'http://127.0.0.1:5000/order', true)
        xml.setRequestHeader('cookieforuser', cookieforuser)
        xml.setRequestHeader('userdata', userdata)
        xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        body+= "food_id=" + food_name.getAttribute('food_id') + '&business_id=' + food_business_name.getAttribute('business_id') + '&user_id=' + userdata + "&food_number=" + food_number.innerText + '&all_price_number=' + all_price_number.innerHTML + "&food_singe_price=" + food_singe_price.innerText + "&address=" + address_select.value
        console.log("body:" + body)
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
                    else if(xml.responseText==='YES'){
                        alert('下单成功，请及时付款')
                    }
                    else{
                        alert('未知错误')
                    }
                }
            }
        }
    }
})
xml=new XMLHttpRequest()
url="http://127.0.0.1:5000/food?id="+food_name.getAttribute('food_id').toString()
xml.open('GET', url)
xml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
console.log("cookie:  "+document.cookie.split(";")[0].split("=")[1])
xml.setRequestHeader("cookieforuser", cookieforuser);
xml.setRequestHeader("userdata", userdata);
xml.send();
xml.onreadystatechange=function() {
            if (xml.readyState === 4 && xml.status === 200){
                xml.onload = function () {
                    console.log(xml.responseText)
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
                        let data=JSON.parse(xml.responseText)
                        food_name.innerText=data['food_name']
                        food_name.setAttribute('food_id',data['food_id'])
                        food_photo.style.background='url('+data['food_image']+')'
                        food_photo.style.backgroundSize="100% 100%"
                        food_business_name.innerText=data['food_business_name']
                        food_describe.innerText=data['food_description']
                        food_singe_price.innerText=data['food_price']
                        all_price_number.innerText='0'
                        food_business_name.setAttribute("business_id",data['food_business'])
                        food_business_name.addEventListener('click',function (){
                            window.location="http://127.0.0.1:5000/business_data?id="+data['food_business'].toString()
                        })
                        for(let i of data['address'] ){
                            console.log(i.toString())
                            let options=document.createElement('option')
                            options.value=i.toString()
                            options.innerText=i.toString()
                            address_select.appendChild(options)
                        }
                        console.log("data['address']:"+data['address'])

                    }

                }
            }

        }