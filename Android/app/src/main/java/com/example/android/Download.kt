package com.example.android

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.databinding.ActivityDownloadBinding
import java.util.ArrayList
import kotlin.concurrent.thread

lateinit var binding_download:ActivityDownloadBinding

class Download : AppCompatActivity() {
    val booklist=ArrayList<book>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        binding_download= ActivityDownloadBinding.inflate(layoutInflater)
        val rootView: View = binding_download.root
        setContentView(rootView)
        val bookDao:BookDao = AppDatabase.getDatabase(this).bookDao()

        val booklist=ArrayList<book>()
        binding_download.textView3.setTextColor(Color.BLUE)
        binding_download.textView3.text = "本地下载"
        Log.d("Download","${binding_download.textView3.text}--------------------------------------------------------------")
        val layoutManager = LinearLayoutManager(this)
        binding_download.recyclerView.layoutManager = layoutManager
        val adapter = bookAdaterForDownload(booklist,this)
        binding_download.recyclerView.adapter = adapter
            thread {
                booklist.clear()
                for(book in bookDao.loadAllBooks())
                {
                    booklist.add(book(book.bookName,book.bookAuthor,book.bookPhoto,book.bookIntroduction))
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }

            }


    }

    override fun onDestroy() {
        super.onDestroy()
        booklist.clear()
    }
}