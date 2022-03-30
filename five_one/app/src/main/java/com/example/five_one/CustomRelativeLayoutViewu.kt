package com.example.five_one
import android.content.Context


import android.text.TextUtils

import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.widget.*

import com.example.five_one.R


class CustomTitleBar(context: Context, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {
    private val button: Button
    private var text:String
    private var editText:EditText
    private var editText2:EditText
    private var editText3:EditText
    private val textView: TextView
    fun setText(text:String){
        this.text=text
    }
    fun getTitle():String{
        return this.editText3.text.toString()
    }
    fun setLight(boolean: Boolean){
        if(boolean){
            this.setBackgroundColor(Color.WHITE)
            return
        }
        else{
            this.setBackgroundColor(Color.BLACK)
        }

        Log.d("11111111",boolean.toString()+111111111111111)
    }
    fun clearText(){
        this.textView.text=""
    }
    init {
        LayoutInflater.from(context).inflate(R.layout.view_five_one, this, true)
        this.text=""
        editText= findViewById(R.id.editText1)
        editText2= findViewById(R.id.editText2)
        editText3= findViewById(R.id.editText3)
        textView = findViewById<View>(R.id.textView) as TextView
        button = findViewById<View>(R.id.button) as Button
        button.setOnClickListener {
            textView.setText("密码:"+editText.text)
            textView.append("\n")
            textView.append("账号:"+editText2.text)
            Toast.makeText(context,"显示文本",Toast.LENGTH_SHORT).show()
        }

}}