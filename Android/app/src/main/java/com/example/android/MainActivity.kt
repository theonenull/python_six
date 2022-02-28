package com.example.android

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.android.databinding.ActivityMainBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import android.text.Spanned

import android.text.style.UnderlineSpan




lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.textview.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG)
        val rootView: View = binding.root
        setContentView(rootView)
        val prefs = getSharedPreferences("person", MODE_PRIVATE)
        val isRemember = prefs.getBoolean("remember_password", false)
        if (isRemember) {
            val username = prefs.getString("username", "")
            val password = prefs.getString("password", "")
            binding.editText.setText(username)
            binding.editText1.setText(password)
            binding.checkBox.isChecked = true
        }
        binding.textview.setOnClickListener {
            val intent=Intent(this,DetailActivity::class.java)
            startActivity(intent)
        }
        binding.editText1.setTransformationMethod(
            PasswordTransformationMethod
                .getInstance()
        )
        binding.checkBox1.setOnClickListener {
            if (binding.checkBox1.isChecked) {
                binding.editText1.setTransformationMethod(
                    HideReturnsTransformationMethod
                        .getInstance()
                )
            } else {
                binding.editText1.setTransformationMethod(
                    PasswordTransformationMethod
                        .getInstance()
                )
            }
        }
        binding.buttonLogin.setOnClickListener {
            val username: String = binding.editText.text.toString()
            val password: String = binding.editText1.text.toString()
            if (username.length < 8 || username.length > 12 || password.length < 8 || password.length > 12) {
                Toast.makeText(this, "请输入8—12位账号及密码", Toast.LENGTH_SHORT).show()
            } else {
                val client = OkHttpClient()

                val requestBody =
                    FormBody.Builder().add("userName", username).add("passWord", password).build()

                Log.d("MainActivity", "web")


                thread {
                    val request = Request.Builder().url("http://39.107.65.181:8989/user/login")
                        .post(requestBody).build()
                    val response = client.newCall(request).execute()
                    val responseDate = response.body?.string()
                    if (responseDate == "true") {
                        val editor = prefs.edit()
                        if (binding.checkBox.isChecked) {
                            editor.putBoolean("remember_password", true)
                            editor.putString("username", username)
                            editor.putString("password", password)
                        } else {
                            editor.clear()
                        }
                        editor.apply()
                        val intent = Intent(this, Find::class.java)
                        intent.putExtra("username", username)
                        startActivity(intent)
                    } else {
                        Looper.prepare()
                        Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show()
                        Looper.loop()
                    }
                }
            }
//            }catch(e: Exception){
//                    Log.d("--------------------","HERRRRRRRRrrrrrrrrrrrrrrwwwwww")
//                e.printStackTrace()
//                Toast.makeText(this,"无法正常访问",Toast.LENGTH_SHORT).show()
//            }
        }
//        val url=URL("")
//        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
//        conn.connectTimeout = 5000
//        conn.requestMethod = "GET"
//        if (conn.responseCode == 200) {
//            val inputStream: InputStream = conn.inputStream
//            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
//            picture.saveImageToGallery(this,bitmap)
//        }
        binding.fabMain.setOnClickListener {
            val intentMainToDownload = Intent(this, Download::class.java)
            startActivity(intentMainToDownload)
        }
        binding.buttonRegister.setOnClickListener {

            val client = OkHttpClient()
            val username: String = binding.editText.text.toString()
            val password: String = binding.editText1.text.toString()
            if (username.length < 8 || username.length > 12 || password.length < 8 || password.length > 12) {
                Toast.makeText(this, "请输入8—12位账号及密码", Toast.LENGTH_SHORT).show()
            } else {
                val requestBody =
                    FormBody.Builder().add("userName", username).add("passWord", password).build()
                //等待修改
                Log.d("MainActivity", "web")
                thread {
                    val request =
                        Request.Builder().url("http://39.107.65.181:8989/user/insert")
                            .post(requestBody)
                            .build()
                    val response = client.newCall(request).execute()
                    val responseDate = response.body?.string()
                    Looper.prepare()
                    Toast.makeText(this, responseDate, Toast.LENGTH_SHORT).show()
                    Looper.loop()
//                if(responseDate!=null){
//                    val json= JSONObject(responseDate)
//                    //??
//                    val statue=json.getString("statue")
//                    if(statue=="success"){
//                        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show()
//                    }
//                    else if(statue=="username_attention"){
//                        Toast.makeText(this,"用户名已被占用",Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show()
//                    }
//                }
                }
//            }catch(e: Exception){
//                e.printStackTrace()
//                Toast.makeText(this,"无法正常访问",Toast.LENGTH_SHORT).show()
//            }
            }
        }
    }


}