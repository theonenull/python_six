package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.android.databinding.ActivityDetailBinding
import com.example.android.databinding.ActivityMainBinding

lateinit var _binding: ActivityDetailBinding
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        val rootView: View = _binding.root
        setContentView(rootView)
        _binding.button.setOnClickListener {
            Toast.makeText(this,"Thank you for watching ^_^",Toast.LENGTH_SHORT).show()

            finish()
        }


    }
}