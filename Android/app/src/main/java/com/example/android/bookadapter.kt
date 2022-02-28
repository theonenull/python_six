package com.example.android

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

class bookadapter(val bookList: List<book>, val Activity: Activity,var username:String) :

    RecyclerView.Adapter<bookadapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val book_img: ImageView = view.findViewById(R.id.book_img)
        val book_name: TextView = view.findViewById(R.id.book_name)
        val book_auther: TextView = view.findViewById(R.id.book_author)
        val text: TextView = view.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookadapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

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
        holder.book_name.setOnClickListener { view->
            Snackbar.make(view, "对本书有兴趣？", Snackbar.LENGTH_SHORT)
                .setAction("收藏") {
                    thread {
                        try {
                            val bookname = book.bookName
                            val client1 = OkHttpClient()
                            val request1 =
                                Request.Builder()
                                    .url("http://39.107.65.181:8989/userBook/insert?bookName=$bookname&userName=$username")
                                    .get()
                                    .build()
                            val response1 = client1.newCall(request1).execute()
                            val v = response1.body?.string()
//                      /userBook/insert
                            if(v=="true"){
                            Looper.prepare()
                            Toast.makeText(Activity.baseContext, "收藏完成", Toast.LENGTH_SHORT).show()
                            Looper.loop()
                            }
                            else{
                                Looper.prepare()
                                Toast.makeText(Activity.baseContext, "收藏失败", Toast.LENGTH_SHORT).show()
                                Looper.loop()
                            }
                        }
                        catch (e:Exception){
                            e.printStackTrace()
                        }

                    }
                }

                .show()
        }
        holder.book_img.setOnLongClickListener{view->
            Snackbar.make(view, "对本书有兴趣？", Snackbar.LENGTH_SHORT)
                .setAction("下载"){
                    thread{
                        var bookDao=AppDatabase.getDatabase(Activity.baseContext).bookDao()
                        book.id=bookDao.insertBook(book(book.bookName, book.bookAuthor,book.bookPhoto,book.bookIntroduction))
                        Looper.prepare()
                        Toast.makeText(Activity.baseContext,"下载完成",Toast.LENGTH_SHORT).show()
                        Looper.loop()

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

    private fun getBitmapFromURL(src: String): Bitmap {
        try {

            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            val myBitmap: Bitmap = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned")
            return myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Exception", e.toString());
            val url = URL("https://img-blog.csdnimg.cn/20190731174649287.png")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            val myBitmap: Bitmap = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned")
            return myBitmap;
        }
    }


}