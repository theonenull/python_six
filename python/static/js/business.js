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
let obj = {};
let cookie=document.cookie.replace(' ','')
cookie=cookie.replace(' ','')
cookie=cookie.replace('\n','')
cookie=cookie.replace('\n','')
for (let entry of cookie.split(";")) {
    let pair = entry.split("=");
    obj[pair[0].toString()] = pair[1].toString();
}
console.log(obj)
let cookieforuser=obj['cookieforuser']
let userdata=obj['userdata']
console.log('userdata '+userdata)
console.log('cookieforuser '+cookieforuser)
let person_massage=document.getElementById('person_massage')
document.querySelector('#header_photo').addEventListener('mouseover',function(){
    person_massage.style.display="block"
    person_massage.setAttribute('display','block')
})
document.querySelector('#person_massage').addEventListener('mouseleave',function(){
    person_massage.style.display="none"
    person_massage.setAttribute('display','none')

})
document.getElementById('talk_massage').addEventListener('click',function (){
    window.location="http://127.0.0.1:5000/talkroom"
})
document.querySelector('#header_text').addEventListener('mouseover',function(){
    person_massage.style.display="none"
})

document.querySelector('#header_name').addEventListener('mouseover',function(){
    person_massage.style.display="none"
})

document.querySelector('#add_food_button_exit').addEventListener('click',function(){

    document.getElementById('add_food_input_image').value=''
    document.getElementById('add_food_input_describe').value=''
    document.getElementById('add_food_input_price').value=''
    document.getElementById('add_food_input_name').value=''
    document.querySelector('#add_food').style.display="none"
    document.getElementById('add_food_photo').style.background='white'
})
document.getElementById('add_food_button_showphoto').addEventListener('click',function (){
    document.getElementById('add_food_photo').style.background='url('+document.getElementById('add_food_input_image').value+')'
    document.getElementById('add_food_photo').style.backgroundSize="100% 100%"
})
document.getElementById('add_food_button_commit').addEventListener('click',function (){

    xml=new XMLHttpRequest()
    let url='http://127.0.0.1:5000/food/addition'
    let food_name=document.getElementById('add_food_input_name')
    let food_describe=document.getElementById('add_food_input_describe')
    let food_image=document.getElementById('add_food_input_image')
    let food_price=document.getElementById('add_food_input_price')
    if(food_price.value === '' || food_image.value === '' || food_describe.value==='' || food_price.value ===''){
        alert('缺失关键数据')
        return
    }
    xml.open('POST',url,true)
    xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xml.setRequestHeader('cookieforuser',cookieforuser)
    xml.setRequestHeader('userdata',userdata)
    xml.send("food_name="+food_name.value+"&food_describe="+food_describe.value+"&food_image="+food_image.value+"&food_price="+food_price.value);
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
                        abert('禁止访问他人数据')
                    }
                    else{
                        alert('添加成功')
                        food_price.value=''
                        food_image.value=''
                        food_name.value=''
                        food_describe.value=''
                    }

                }
            }

        }
})

document.querySelector('#button_to_addfood').addEventListener('click',function(){
    document.querySelector('#add_food').style.display="block"
})
document.getElementById('')

let xml=new XMLHttpRequest()
url="http://127.0.0.1:5000/business"
xml.open('POST',url,true)
xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
xml.send("userdata="+userdata+"&cookieforuser="+cookieforuser);
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
                        abert('禁止访问他人数据')
                    }
                    else{
                        data=JSON.parse(xml.responseText)
                        document.getElementById('header_name').innerText=data['name']
                        document.getElementById('header_photo').style.background='url('+data['photo'].toString()+')'
                        document.getElementById('header_photo').style.backgroundSize='100% 100%'
                        document.getElementById('income').innerText=data['income']
                        document.getElementById('order_number').innerText=data['sale']
                        document.getElementById('criticism').innerText=data['criticism']
                        document.getElementById('admit_number').innerText=data['favor']
                        let food_list=document.getElementById('food_list')
                        console.log("订单："+data['order'])
                        console.log("订单2："+data['order'])
                        for(let i=0 ;i<data['length'];i++){
                            var oTr = document.createElement('tr');
                            td_name=document.createElement('td')
                            td_image=document.createElement('td')
                            td_description=document.createElement('td')
                            td_price=document.createElement('td')
                            td_name.innerText=data['food'][i][1]
                            td_description.innerText=data['food'][i][2]
                            td_price.innerText=data['food'][i][3]
                            div_image=document.createElement('div')
                            div_image.className='table_image'
                            div_image.style.background='url('+data['food'][i][5].toString()+')'
                            div_image.style.backgroundSize="100% 100%"
                            td_image.appendChild(div_image)
                            td_image.className='food_image'
                            oTr.appendChild(td_image)
                            oTr.appendChild(td_name)
                            oTr.appendChild(td_description)
                            oTr.appendChild(td_price)
                            food_list.appendChild(oTr)
                        }
                        let order_list=document.getElementById('table_food')
                        for(let i of data['order']){
                            let tr=document.createElement('tr')
                            let td_food_name=document.createElement('td')
                            td_food_name.innerText=i[2][1]
                            tr.appendChild(td_food_name)
                            let td_food_price=document.createElement('td')
                            td_food_price.innerText=i[2][3]
                            tr.appendChild(td_food_price)
                            let td_food_number=document.createElement('td')
                            td_food_number.innerText=i[0][6]
                            tr.appendChild(td_food_number)
                            let td_food_income=document.createElement('td')
                            td_food_income.innerText=i[0][9]
                            tr.appendChild(td_food_income)
                            let td_food_rider=document.createElement('td')
                            td_food_rider.innerText=i[0][2]
                            tr.appendChild(td_food_rider)
                            let td_food_user=document.createElement('td')
                            td_food_user.innerText=i[1][1]
                            tr.appendChild(td_food_user)
                            let td_food_statue=document.createElement('td')
                            switch (i[0][5]){
                            case SOMETHING_WRONG:
                                td_food_statue.innerText="出现问题"
                                break;
                            case UNPAY:
                                td_food_statue.innerText="未付款"
                                break;
                            case UN_ACCEPT:
                                td_food_statue.innerText="未接单"
                                break;
                            case ON_THE_WAY:
                                td_food_statue.innerText="已经送达"
                                break;
                            case ALLREADY_REACH:
                                td_food_statue.innerText="出现问题"
                                break;
                            }
                            tr.appendChild(td_food_statue)
                            document.getElementById('order_list').appendChild(tr)
                        }
                    }

                }
            }

        }