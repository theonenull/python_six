package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android.databinding.ActivityFindBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.Exception
import android.R.array
import android.annotation.SuppressLint
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import kotlin.concurrent.thread


lateinit var find_binding:ActivityFindBinding
class Find : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)
        find_binding= ActivityFindBinding.inflate(layoutInflater)
        val rootView: View = find_binding.root
        setContentView(rootView)
        //加载recyclerview

        val book_list =ArrayList<book>()
        val layoutManager = LinearLayoutManager(this)
        find_binding.recyclerView.layoutManager = layoutManager
        val adapter = bookadapter(book_list ,this,intent.getStringExtra("username").toString())
        find_binding.recyclerView.adapter=adapter
        thread {
            book_list.clear()
            try{
                    val client1 = OkHttpClient()
                    val request1= Request.Builder().url("http://39.107.65.181:8989/getBook/show").build()
                    val response1=client1.newCall(request1).execute()

                    val responseDate1=response1.body()?.string()
                    if(responseDate1!=null){
                        val json1= JSONArray(responseDate1)
                        for(i in 0 until json1.length()){
                            Log.d("-------------","${json1.length()}--------------------------------------------")
                            val jsonObject=json1.getJSONObject(i)
                            book_list.add(book(jsonObject.getString("bookName"),jsonObject.getString("bookAuthor"),jsonObject.getString("bookPhoto"),jsonObject.getString("bookIntroduction")))
                            Log.d("-------------",book_list.toString())

                    }


                            runOnUiThread {
                                adapter.notifyDataSetChanged()
                            }

                    }
        }
            catch (e:Exception){

            }
        }


        //转到个人页面
        find_binding.fab.setOnClickListener{
            val intent_person= Intent(this,person::class.java)
            intent_person.putExtra("username",intent.getStringExtra("username"))
            startActivity(intent_person)
        }

        //开始搜索
        find_binding.button.setOnClickListener{
           // try{
            thread{
                val client = OkHttpClient()
                val bookname= find_binding.editText2.text.toString()

                //等待修改
                Log.d("MainActivity","web")
                val request= Request.Builder().url("http://39.107.65.181:8989/getBook/search?bookName=$bookname").get().build()
                val response=client.newCall(request).execute()
                val responseDate=response.body()?.string()
                if(responseDate!=null){

                    book_list.clear()
                    val json= JSONArray(responseDate)
                    for(i in 0 until json.length()){
                           val jsonObject=json.getJSONObject(i)
                            book_list.add(book(jsonObject.getString("bookName"),jsonObject.getString("bookAuthor"),jsonObject.getString("bookPhoto"),jsonObject.getString("bookIntroduction")))
                    }
                    runOnUiThread {
                    adapter.notifyDataSetChanged()
                    }
                }
            }
//            }catch(e: Exception){
//                e.printStackTrace()
//                Toast.makeText(this,"无法正常访问", Toast.LENGTH_SHORT).show()
//            }
      //  }

        }
    }
}