package com.example.five_one

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

import android.annotation.SuppressLint;
@SuppressLint("AppCompatCustomView")


class selfImageView(context: Context, attrs: AttributeSet?,defStyleAttr:Int) : ImageView(context,attrs,defStyleAttr){
    constructor(context: Context):this(context,null)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
}