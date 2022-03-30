package com.example.five_one

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class myviewGroup(context: Context, attrs: AttributeSet,var list: List<Int>) : RecyclerView(context,attrs){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
//    boolean changed, int l, int t, int r, int b
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        var count:Int = childCount;
        var currentHeight :Int= 0;
        for (i in 0 until  count ){
            val view:View= getChildAt(i);
            val height:Int = view.measuredHeight;
            val width:Int = view.measuredWidth;
            view.layout(left, currentHeight, left + width, currentHeight + height);
            currentHeight += height;
        }
    }


}