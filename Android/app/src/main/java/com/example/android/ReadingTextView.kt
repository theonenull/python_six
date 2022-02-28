package com.example.android

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class ReadingTextView(val content:Context) : androidx.appcompat.widget.AppCompatTextView(content) {

    fun getEstimatedLength():Int {
        val height:Int = height
        val lineHeight:Int = lineHeight
        val linecount: Int = height / lineHeight

        val textsize:Float = getTextSize()
        val linewords:Int = (width / textsize).toInt()
        return linecount * linewords

    }
}
