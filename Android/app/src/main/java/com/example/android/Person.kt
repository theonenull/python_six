package com.example.android

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.databinding.ActivityPersonBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

lateinit var binding_person: ActivityPersonBinding

class person : AppCompatActivity() {
    var userName: String = ""
    val booklist = ArrayList<book>()

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        binding_person = ActivityPersonBinding.inflate(layoutInflater)
        val rootView: View = binding_person.root
        setContentView(rootView)
        userName = intent.getStringExtra("username").toString()
        binding_person.download.setOnClickListener {
            val intentDownload = Intent(this, Download::class.java)
            intentDownload.putExtra("username", intent.getStringExtra("username"))
            startActivity(intentDownload)
        }
        binding_person.ButtonForNew.setOnClickListener {

        }
        binding_person.textView2.setText(userName + "的书架")
        val layoutManager = LinearLayoutManager(this)
        binding_person.recyclerView.layoutManager = layoutManager
        var adapter =
            bookadapterForPerson(booklist, this, intent.getStringExtra("username").toString())
        binding_person.recyclerView.adapter = adapter
        getListForLike(adapter)
        binding_person.ButtonForNew.setOnClickListener {
            Toast.makeText(this,"正在刷新",Toast.LENGTH_SHORT).show()
                getListForLike(adapter)
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getListForLike(adapter: bookadapterForPerson) {
        booklist.clear()
        thread {
            val client1 = OkHttpClient()
            val request1 =
                Request.Builder()
                    .url("http://39.107.65.181:8989/userBook/list?userName=$userName").get()
                    .build()
            val response1 = client1.newCall(request1).execute()
            val v = response1.body?.string()
            val json = JSONArray(v)
            if(json.length()==0){
                booklist.clear()
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }

            }
            else {
                for (i in 0 until json.length()) {
                    val bookname = json.getJSONObject(i).getString("bookName")
                    val client2 = OkHttpClient()
                    Log.d("-------------", "---------------------------")
                    val request2 =
                        Request.Builder()
                            .url("http://39.107.65.181:8989/getBook/queryById?bookName=$bookname")
                            .get()
                            .build()
                    val response2 = client2.newCall(request2).execute()
                    val v2 = response2.body?.string()
                    val json2 = JSONObject(v2)
                    booklist.add(
                        book(
                            json2.getString("bookName"),
                            json2.getString("bookAuthor"),
                            json2.getString("bookPhoto"),
                            json2.getString("bookIntroduction")
                        )
                    )
                }
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}