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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.w3c.dom.Text
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class bookAdaterForDownload(val bookList:List<book>, val Activity: Activity):

    RecyclerView.Adapter<bookAdaterForDownload.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookAdaterForDownload.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)

        return ViewHolder(view)
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val book_img: ImageView = view.findViewById(R.id.book_img)
        val book_name: TextView = view.findViewById(R.id.book_name)
        val book_auther:TextView = view.findViewById(R.id.book_author)
        val text:TextView=view.findViewById(R.id.text)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        try{
            Picasso.get().load(book.bookPhoto).into(holder.book_img);
        }catch (e:Exception){
            holder.book_img.setImageResource(R.drawable.icon_me)
        }
        holder.text.text=book.bookIntroduction
        holder.book_name.text = book.bookName
        holder.book_auther.text = book.bookAuthor
        //长按

        //按
        holder.book_img.setOnClickListener {

//            val intent = Intent(Activity.baseContext, person::class.java)
            val intent= Intent(Activity.baseContext,readActivity::class.java)
            intent.putExtra("bookname",book.bookName)
            Activity.startActivity(intent)


        }
    }
    override fun getItemCount(): Int = bookList.size

    private fun getBitmapFromURL(src:String):Bitmap{
        var bitmap: Bitmap
        try {

            val url=URL(src)
            val connection:HttpURLConnection=url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input:InputStream = connection.inputStream
            bitmap  = BitmapFactory.decodeStream(input)
            Log.e("Bitmap","returned")
            input.close()

        } catch ( e: IOException) {
            e.printStackTrace()
            Log.e("Exception",e.toString());
            val url=URL("https://img-blog.csdnimg.cn/20190731174649287.png")
            val connection:HttpURLConnection=url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input:InputStream = connection.inputStream
            bitmap= BitmapFactory.decodeStream(input)
            Log.d("Bitmap","returned")

        }
        return  bitmap
    }


}