package com.example.five_twoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllImageView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_image_view)
        var list=intent.getStringArrayListExtra("list")
        var adapter= list?.let { Alladapter(it,this) }
        val layoutManager = GridLayoutManager(this,2)
//create the layoutmanage matching the layout
        var recyclerview2:RecyclerView=findViewById(R.id.recyclerview2)
        recyclerview2.layoutManager = layoutManager  //recyclerview match the layoutmanger
        recyclerview2.adapter=adapter //recyclerview match the adapter
    }
}