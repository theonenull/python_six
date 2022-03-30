package com.example.five_twoo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomTitleBar(context: Context, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {
    var number=3
    var adapter:adapter
    var list=ArrayList<String>()
    var boolean: Boolean=true
    var listForUser=ArrayList<String>()
    var layoutManager: RecyclerView.LayoutManager
    var textView:TextView
    var recyclerView:RecyclerView
    init{
        LayoutInflater.from(context).inflate(R.layout.mylayout, this, true)
        repeat(10){
            list.add("https://tse1-mm.cn.bing.net/th/id/R-C.4d9cd2e53dddfc238a06e750b73cd023?rik=MsMCKPGumufOyQ&riu=http%3a%2f%2fwww.desktx.com%2fd%2ffile%2fwallpaper%2fscenery%2f20170209%2fc2accfe637f86fb6f11949cb8651a09b.jpg&ehk=ia2TVXcow6ygWUVZ1yod5xH4aGd8565SYn6CRpxkNoo%3d&risl=&pid=ImgRaw&r=0")
        }
        if(list.size<=9){
            listForUser=list
            boolean=false
        }
        else{
            for(i in 0 until 8){
                listForUser.add(list.get(i))
            }
            boolean=true
            listForUser.add("https://tse4-mm.cn.bing.net/th/id/OIP-C.bC1Do7gVCKm_3VvdZxOylwHaHa?w=172&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7")
        }
        textView=findViewById(R.id.textView)
        recyclerView=findViewById(R.id.recyclerview)
        adapter=adapter(listForUser,context,boolean,list)
        layoutManager = GridLayoutManager(context,3)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
    }
    fun setData(list:ArrayList<String>){
        listForUser= ArrayList<String>()
        if(list.size<=9){
            listForUser=list
            boolean=false
            if(list.size==1){
                number=1
            }
            else if(list.size in 2..4){
                number=2
            }
            else if(list.size>=5){
                number=3
            }
            else{
                number=3
            }

        }

        else{
            for(i in 0 until 8){
                listForUser.add(list.get(i))
            }
            number=3
            boolean=true
            listForUser.add("https://tse4-mm.cn.bing.net/th/id/OIP-C.bC1Do7gVCKm_3VvdZxOylwHaHa?w=172&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7")
        }
        Log.d("number=====","1111111111111"+number.toString())
        adapter=adapter(listForUser,context,boolean,list)
        layoutManager = GridLayoutManager(context,number)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
//        number=3

    }





    }