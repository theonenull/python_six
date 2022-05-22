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
let xml=new XMLHttpRequest()
url="http://127.0.0.1:5000/ridermassage"
xml.open('GET',url,true)
xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
xml.setRequestHeader('cookieforuser',cookieforuser)
xml.setRequestHeader('userdata',userdata)
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
                    else {
                        data = JSON.parse(xml.responseText)
                        console.log(data)
                        document.getElementById('income').innerText=data['income']
                        document.getElementById('order_number').innerText=data['send']
                        document.getElementById('admit_number').innerText=data['favor']
                        document.getElementById('criticism').innerText=data['criticism']
                        document.getElementById('header_photo').style.background='url('+data['photo']+')'
                        document.getElementById('header_photo').style.backgroundSize='100% 100%'

                        for(let i of data['order_for_select']){
                            let tr_item=document.createElement('tr')
                            let th_name=document.createElement('th')
                            th_name.addEventListener('click',function(){
                                let xml=new XMLHttpRequest()
                                let url = "http://127.0.0.1:5000/accept"
                                xml.open('POST',url,true)
                                xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                                xml.setRequestHeader('cookieforuser',cookieforuser)
                                xml.setRequestHeader('userdata',userdata)
                                xml.send('order_id='+i[0][7]);
                                xml.onreadystatechange=function (){
                                    if (xml.readyState === 4 && xml.status === 200){
                                        if(xml.responseText==='NO'){
                                            alert('服务端出现问题')
                                        }
                                        else if (xml.responseText==='CHANGED'){
                                            alert('查无此人！请勿自行修改数据')
                                        }
                                        else if(xml.responseText==='BAN'){
                                            alert('禁止访问他人数据')
                                        }
                                        else if(xml.responseText==="SUCCESS"){
                                            alert('接单成功，请刷新')
                                        }
                                        else{
                                             alert('接单失败')
                                        }
                                    }
                                }
                            })
                            th_name.innerText=i[2][1]
                            let title='状态：'
                            switch (i[0][5]){
                            case SOMETHING_WRONG:
                                title+="出现问题\n"
                                break;
                            case UNPAY:
                                title+="未付款\n"
                                break;
                            case UN_ACCEPT:
                                title+="未接单\n"
                                break;
                            case ON_THE_WAY:
                                title+="已经送达\n"
                                break;
                            case ALLREADY_REACH:
                                title+="出现问题\n"
                                break;
                            }
                            title+="数量："+i[0][6]+'\n'
                            title+="单价："+i[2][3]
                            th_name.title=title
                            tr_item.appendChild(th_name)
                            let th_address=document.createElement('th')
                            th_address.innerText=i[0][8]
                            tr_item.appendChild(th_address)
                            let th_time=document.createElement('th')
                            th_time.innerText=i[0][4]
                            tr_item.appendChild(th_time)
                            let th_income=document.createElement('th')
                            th_income.innerText='5'
                            tr_item.appendChild(th_income)
                            let th_business=document.createElement('th')
                            th_business.innerText=i[3][1]
                            tr_item.appendChild(th_business)
                            let th_user=document.createElement('th')
                            th_user.innerText=i[1][1]
                            tr_item.appendChild(th_user)
                            document.getElementById('order_list').appendChild(tr_item)
                        }
                        for(let i of data['order_for_send']){
                            let tr_item=document.createElement('tr')
                            let th_name=document.createElement('th')
                            th_name.addEventListener('click',function(){
                                let xml=new XMLHttpRequest()
                                let url = "http://127.0.0.1:5000/send"
                                xml.open('POST',url,true)
                                xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                                xml.setRequestHeader('cookieforuser',cookieforuser)
                                xml.setRequestHeader('userdata',userdata)
                                xml.send('order_id='+i[0][7]);
                                xml.onreadystatechange=function (){
                                    if (xml.readyState === 4 && xml.status === 200){
                                        if(xml.responseText==='NO'){
                                            alert('服务端出现问题')
                                        }
                                        else if (xml.responseText==='CHANGED'){
                                            alert('查无此人！请勿自行修改数据')
                                        }
                                        else if(xml.responseText==='BAN'){
                                            alert('禁止访问他人数据')
                                        }
                                        else if(xml.responseText==="SUCCESS"){
                                            alert('已送达，请刷新')
                                        }
                                        else{
                                             alert('接单失败')
                                        }
                                    }
                                }
                            })
                            th_name.innerText=i[2][1]
                            let title='状态：'
                            switch (i[0][5]){
                            case SOMETHING_WRONG:
                                title+="出现问题\n"
                                break;
                            case UNPAY:
                                title+="未付款\n"
                                break;
                            case UN_ACCEPT:
                                title+="未接单\n"
                                break;
                            case ON_THE_WAY:
                                title+="已经送达\n"
                                break;
                            case ALLREADY_REACH:
                                title+="出现问题\n"
                                break;
                            }
                            title+="数量："+i[0][6]+'\n'
                            title+="单价："+i[2][3]
                            th_name.title=title
                            tr_item.appendChild(th_name)
                            let th_address=document.createElement('th')
                            th_address.innerText=i[0][8]
                            tr_item.appendChild(th_address)
                            let th_time=document.createElement('th')
                            th_time.innerText=i[0][4]
                            tr_item.appendChild(th_time)
                            let th_income=document.createElement('th')
                            th_income.innerText='5'
                            tr_item.appendChild(th_income)
                            let th_business=document.createElement('th')
                            th_business.innerText=i[3][1]
                            tr_item.appendChild(th_business)
                            let th_user=document.createElement('th')
                            th_user.innerText=i[1][1]
                            tr_item.appendChild(th_user)
                            document.getElementById('order_to_send').appendChild(tr_item)
                        }

                    }

                }
            }

        }