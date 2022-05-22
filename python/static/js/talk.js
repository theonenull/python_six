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
$(document).ready(function () {
    let xml=new XMLHttpRequest()
    xml.open('GET',"http://127.0.0.1:5000/massage",true)
    xml.setRequestHeader('cookieforuser',cookieforuser)
    xml.setRequestHeader('userdata',userdata)
    xml.send()
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
                    let data=JSON.parse(xml.responseText)
                    for(let i of data['massage']){
                        let massage_item=document.createElement('div')
                        massage_item.className='massage_item'

                        let massage_item_name=document.createElement('div')
                        massage_item_name.innerText=i[1]
                        massage_item_name.className="massage_item_name"
                        massage_item.appendChild(massage_item_name)

                        let massage_item_identity=document.createElement('div')
                        massage_item_identity.innerText=i[4]
                        massage_item_identity.className='massage_item_identity'
                        massage_item.appendChild(massage_item_identity)

                        let massage_item_time=document.createElement('div')
                        massage_item_time.innerText=i[3]
                        massage_item_time.className='massage_item_time'
                        massage_item.appendChild(massage_item_time)

                        let br=document.createElement('br')
                        massage_item.appendChild(br)

                        let massage_item_photo=document.createElement('div')
                        massage_item_photo.style.background='url('+i[2].toString()+')'
                        massage_item_photo.style.backgroundSize="100% 100%"
                        massage_item_photo.className='massage_item_photo'
                        massage_item.appendChild(massage_item_photo)

                        let massage_item_massage=document.createElement('div')
                        massage_item_massage.innerText=i[0]
                        massage_item_massage.className='massage_item_massage'
                        massage_item.appendChild(massage_item_massage)

                        document.getElementById('massage').appendChild(massage_item)
                        document.getElementById('massage').scrollTop = document.getElementById('massage').scrollHeight;
                        console.log("成员："+data['member'].toString())
                        $('talkroom_member_name').empty()

                    }
                for(let j of data['member']){
                            console.log(j.toString())
                            let div_name=document.createElement('div')
                            $('talkroom_member_name').empty()
                            div_name.className='talkroom_member_name_item'
                            div_name.innerText=j[1].toString()
                            console.log('name')
                            document.getElementById('talkroom_member_name').appendChild(div_name)
                        }
                document.getElementById('person_massage_name').innerText=data.name
                document.getElementById('person_massage_photo').style.background='url('+data.photo+')'
                document.getElementById('person_massage_photo').style.backgroundSize='100% 100%'
                }

        }
    }
})
const socket1 = io("http://127.0.0.1:5000/talkRoomData?userdata="+userdata);
socket1.on('join_member',function (data){
    document.getElementById('talkroom_member_name').innerText=''
    $('talkroom_member_name').empty();
    for(let j of data.member){
            let div_name=document.createElement('div')
            div_name.className='talkroom_member_name_item'
            div_name.innerText=j[1].toString()
            document.getElementById('talkroom_member_name').appendChild(div_name)
        }
    console.log('talkroom_member_name:'+data.toString())
})
socket1.on('in',function (data){
        console.log(data)
    let massage_item=document.createElement('div')
    massage_item.className='massage_item'

    let massage_item_name=document.createElement('div')
    massage_item_name.innerText=data.name
    massage_item_name.className="massage_item_name"
    massage_item.appendChild(massage_item_name)

    let massage_item_identity=document.createElement('div')
    massage_item_identity.innerText=data.identity
    massage_item_identity.className='massage_item_identity'
    massage_item.appendChild(massage_item_identity)

    let massage_item_time=document.createElement('div')
    massage_item_time.innerText=data.time
    massage_item_time.className='massage_item_time'
    massage_item.appendChild(massage_item_time)

    let br=document.createElement('br')
    massage_item.appendChild(br)

    let massage_item_photo=document.createElement('div')
    massage_item_photo.style.background='url('+data.photo.toString()+')'
    massage_item_photo.style.backgroundSize="100% 100%"
    massage_item_photo.className='massage_item_photo'
    massage_item.appendChild(massage_item_photo)

    let massage_item_massage=document.createElement('div')
    massage_item_massage.innerText=data.massage.toString()
    massage_item_massage.className='massage_item_massage'
    massage_item.appendChild(massage_item_massage)

    document.getElementById('massage').appendChild(massage_item)
    document.getElementById('massage').scrollTop = document.getElementById('massage').scrollHeight;
    }
)
socket1.on('roomMassage', function(data) {
    console.log(data)
});
document.getElementById('input_button').addEventListener('click',function (){
    if(document.getElementById('input_text').innerText.toString()===''){
        alert('不得为空')
        return
    }
    let xml=new XMLHttpRequest()
    xml.open('POST',"http://127.0.0.1:5000/massage",true)
    xml.setRequestHeader('cookieforuser',cookieforuser)
    xml.setRequestHeader('userdata',userdata)
    xml.setRequestHeader("Content-Type"
			, "application/x-www-form-urlencoded");
    xml.send("massage="+document.getElementById('input_text').innerText.toString())
    document.getElementById('input_text').innerText=''
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
                    console.log("成功")
                }

        }
    }
})

