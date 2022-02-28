package com.example.android

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.android.databinding.ActivityReadBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import kotlin.concurrent.thread

lateinit var bindingForReading:ActivityReadBinding
class readActivity : AppCompatActivity() {
    private var contentList=ArrayList<String>()
    lateinit var readTextView: TextView
    private var page:Int = 0
    var or : Boolean = false
    private  var thebookChapter=ArrayList<bookChapter>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        bindingForReading = ActivityReadBinding.inflate(layoutInflater)
        val rootView: View = bindingForReading.root
        setContentView(rootView)
        var thebookChapter=ArrayList<bookChapter>()
        //绑定
        var bookname:String=""
        bookname = intent.getStringExtra("bookname").toString()
        Toast.makeText(this,bookname,Toast.LENGTH_SHORT).show()
        bindingForReading.textView4.text=bookname

        //申请章节

        text(page,bookname)


        //设置

        bindingForReading.PreviousPage.setOnClickListener {
            if (page==0) Toast.makeText(this,"这是第一章了", Toast.LENGTH_SHORT).show()
            else {
                bindingForReading.readingTextView.setText("")
                text(--page,bookname)
                var number=page+1
                Toast.makeText(this,"第$number"+"章", Toast.LENGTH_SHORT).show()
            }
        }
        bindingForReading.NextPage.setOnClickListener {
            if (page==thebookChapter.size-1) Toast.makeText(this,"这是最后一章了", Toast.LENGTH_SHORT).show()
            else {
                bindingForReading.readingTextView.setText("")
                text(++page,bookname)
                var number=page+1
                Toast.makeText(this,"第$number"+"章", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getChapter(bookname:String){
        val clientForChapter = OkHttpClient()
        val request2 =Request.Builder().url("http://39.107.65.181:8989//getContext/getChapter?bookName=$bookname").build()
        val responseForChapter=clientForChapter.newCall(request2).execute()
        val temp=responseForChapter.body?.string()
        val json = JSONArray(temp)
        for(i in 0 until json.length()){
            thebookChapter.add(bookChapter(json.getJSONObject(i).getString("bookChapter")))
        }
        Log.d("-----------","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
        or=true
        Log.d("-----------", or.toString())
    }

    fun text(i:Int,bookname:String){
        thread {
            try {
                if(!or){
                    getChapter(bookname)
                }
                val bookChapter = thebookChapter[i].name
                runOnUiThread {
                    bindingForReading.textView4.setText(bookChapter)
                }
                val client1 = OkHttpClient()
                val request1 =
                    Request.Builder().url("http://39.107.65.181:8989/getContext/getText?bookName=$bookname&bookChapter=$bookChapter")
                        .build()
                val response1 = client1.newCall(request1).execute()
                val v=response1.body?.string()
                val jsonObject = JSONObject(v)
                val b:String=jsonObject.getString("bookContext")
                load(b)
            }catch (e:Exception){
                runOnUiThread {
                    Toast.makeText(this,"bookname",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun load(inputText:String) {
        var name:String=""
        try{
            val output=openFileOutput("data", Context.MODE_PRIVATE)
            var writer=BufferedWriter(OutputStreamWriter(output))
            writer.use{
                it.write(inputText)
            }
        }catch(e:Exception){
            e.printStackTrace()
        }
        try{
            val input=openFileInput("data")
            val reader=BufferedReader(InputStreamReader(input))
            reader.use{
                reader.forEachLine {
                    name=it
                    runOnUiThread {
                    bindingForReading.readingTextView.append(name)
                }
                } }

            }
        catch (e:Exception){
            e.printStackTrace()
        }

    }
}