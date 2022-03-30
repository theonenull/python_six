package com.example.five_twoo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list=ArrayList<String>()
        repeat(10){
            list.add("https://tse1-mm.cn.bing.net/th/id/R-C.4d9cd2e53dddfc238a06e750b73cd023?rik=MsMCKPGumufOyQ&riu=http%3a%2f%2fwww.desktx.com%2fd%2ffile%2fwallpaper%2fscenery%2f20170209%2fc2accfe637f86fb6f11949cb8651a09b.jpg&ehk=ia2TVXcow6ygWUVZ1yod5xH4aGd8565SYn6CRpxkNoo%3d&risl=&pid=ImgRaw&r=0")
        }
        var customTitleBar:CustomTitleBar=findViewById(R.id.customTitleBar)
        var button1: Button =findViewById(R.id.button)
        var button2:Button=findViewById(R.id.button2)
        var button3:Button=findViewById(R.id.button3)
        var button4:Button=findViewById(R.id.button4)
        button1.setOnClickListener {
            list.clear()
            repeat(1){
                list.add("https://tse1-mm.cn.bing.net/th/id/R-C.4d9cd2e53dddfc238a06e750b73cd023?rik=MsMCKPGumufOyQ&riu=http%3a%2f%2fwww.desktx.com%2fd%2ffile%2fwallpaper%2fscenery%2f20170209%2fc2accfe637f86fb6f11949cb8651a09b.jpg&ehk=ia2TVXcow6ygWUVZ1yod5xH4aGd8565SYn6CRpxkNoo%3d&risl=&pid=ImgRaw&r=0")
            }
            customTitleBar.setData(list)
        }
        button2.setOnClickListener {
            list.clear()
            repeat(7){
                list.add("https://tse1-mm.cn.bing.net/th/id/R-C.4d9cd2e53dddfc238a06e750b73cd023?rik=MsMCKPGumufOyQ&riu=http%3a%2f%2fwww.desktx.com%2fd%2ffile%2fwallpaper%2fscenery%2f20170209%2fc2accfe637f86fb6f11949cb8651a09b.jpg&ehk=ia2TVXcow6ygWUVZ1yod5xH4aGd8565SYn6CRpxkNoo%3d&risl=&pid=ImgRaw&r=0")
            }
            customTitleBar.setData(list)
        }
        button3.setOnClickListener {
            list.clear()
            repeat(3){
                list.add("https://tse1-mm.cn.bing.net/th/id/R-C.4d9cd2e53dddfc238a06e750b73cd023?rik=MsMCKPGumufOyQ&riu=http%3a%2f%2fwww.desktx.com%2fd%2ffile%2fwallpaper%2fscenery%2f20170209%2fc2accfe637f86fb6f11949cb8651a09b.jpg&ehk=ia2TVXcow6ygWUVZ1yod5xH4aGd8565SYn6CRpxkNoo%3d&risl=&pid=ImgRaw&r=0")
            }
            customTitleBar.setData(list)
        }
        button4.setOnClickListener {
            list.clear()
            repeat(10){
                list.add("https://tse1-mm.cn.bing.net/th/id/R-C.4d9cd2e53dddfc238a06e750b73cd023?rik=MsMCKPGumufOyQ&riu=http%3a%2f%2fwww.desktx.com%2fd%2ffile%2fwallpaper%2fscenery%2f20170209%2fc2accfe637f86fb6f11949cb8651a09b.jpg&ehk=ia2TVXcow6ygWUVZ1yod5xH4aGd8565SYn6CRpxkNoo%3d&risl=&pid=ImgRaw&r=0")
            }
            customTitleBar.setData(list)
        }
    }

}
