package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.collections.ArrayList


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val extraData = intent.getIntExtra("fruitimage1",100)
        val extraData1 = intent.getIntExtra("fruitimage3",100)
        image.setImageResource(extraData)
        image1.setImageResource(extraData1)
        val number=intent.getIntExtra("number",0)
        val name=intent.getStringExtra("name")
        text2.text=(number.toString())
        text1.text=name
       button1.setOnClickListener{
           finish()
       }
        button2.setOnClickListener{
            val intent1 = Intent()
            intent1.putExtra("data_return", intent.getIntExtra("position",0))
            setResult(RESULT_OK, intent1)
            finish()
        }
    }

}