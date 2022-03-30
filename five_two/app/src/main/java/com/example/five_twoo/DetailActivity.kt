package com.example.five_twoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlin.concurrent.thread

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var string=intent.getStringExtra("string")
        var image:ImageView=findViewById(R.id.imageForDetail)
        thread {
            runOnUiThread{
                Glide.with(this).load(string).into(image)
            }

        }

    }
}