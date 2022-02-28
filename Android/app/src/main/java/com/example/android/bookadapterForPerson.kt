package com.example.android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class bookadapterForPerson(val bookList: List<book>, val Activity: Activity,val username:String) :

    RecyclerView.Adapter<bookadapterForPerson.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val book_img: ImageView = view.findViewById(R.id.book_img)
        val book_name: TextView = view.findViewById(R.id.book_name)
        val book_auther: TextView = view.findViewById(R.id.book_author)
        val text: TextView = view.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookadapterForPerson.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        try {
            Picasso.get().load(book.bookPhoto).into(holder.book_img);
        } catch (e: Exception) {
            holder.book_img.setImageResource(R.drawable.icon_me)
        }
        holder.book_name.text = book.bookName
        holder.book_auther.text = book.bookAuthor
        holder.text.text = book.bookIntroduction
        holder.book_img.setOnLongClickListener(View.OnLongClickListener {
            val layoutPosition: Int = holder.layoutPosition
            val intent = Intent(Activity.baseContext, DetailActivity::class.java)
            Activity.startActivityForResult(intent, 1)
            true

        })
        holder.book_img.setOnLongClickListener {view->
            Snackbar.make(view, "不感兴趣了？", Snackbar.LENGTH_SHORT)
                .setAction("没错") {
                    thread {
                        try {
                            val bookname = book.bookName
                            val client1 = OkHttpClient()
                            val request1 =
                                Request.Builder()
                                    .url("http://39.107.65.181:8989/userBook/remove?bookName=$bookname&userName=$username")
                                    .get()
                                    .build()
                            val response1 = client1.newCall(request1).execute()
                            val v = response1.body?.string()
//                      /userBook/insert
                            Looper.prepare()
                            Toast.makeText(Activity.baseContext, "取消收藏完成", Toast.LENGTH_SHORT).show()
                            this.notifyDataSetChanged()
                            Looper.loop()

                        }
                        catch (e:Exception){
                            e.printStackTrace()
                        }



                    }
                }

                .show()
            true
        }



        holder.book_img.setOnClickListener { view->
            val intent =Intent(Activity.baseContext,readActivity::class.java)
            intent.putExtra("bookname",book.bookName)
            Activity.startActivityForResult(intent,1)
        }
    }

    override fun getItemCount(): Int = bookList.size



}