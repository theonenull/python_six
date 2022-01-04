package com.example.homework

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.collections.ArrayList
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
private val fruitList = ArrayList<Up>()
var i="true"
class MainActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                val position = data?.getIntExtra("data_return",0)

                position?.let { recyclerview.adapter?.notifyItemRemoved(it) };
                position?.let { fruitList.removeAt(it) };
                recyclerview.adapter?.getItemCount()
                    ?.let { position?.let { it1 -> recyclerview.adapter?.notifyItemRangeChanged(it1, it) } };
                imageview.setImageResource(R.drawable.img)
                Toast.makeText(this, "取关成功", Toast.LENGTH_LONG).show()
            }

        }

    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            if(i=="true"){
                initUps() // 初始化水果数据
            }
        setContentView(R.layout.activity_main)

        var id=2
        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL

        val adapter = UpAdapter(fruitList,imageview,this)
        recyclerview.adapter = adapter


    }
    private fun initUps() {
        repeat(4) {
            fruitList.add(Up("混知", R.drawable.hunzi1,R.drawable.hunzi2,R.drawable.hunzi3,2020))
            fruitList.add(Up("steam资讯", R.drawable.steam1,R.drawable.steam2,R.drawable.steam3,2021))
            fruitList.add(Up("央视新闻", R.drawable.center1,R.drawable.center2,R.drawable.center3,2022))
            fruitList.add(Up("哔哩哔哩漫画", R.drawable.blbl1,R.drawable.blbl2,R.drawable.blbl3,2023))
            i="false"

        }
    }

}