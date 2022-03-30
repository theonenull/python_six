package com.example.five_three

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Shader
import android.graphics.RadialGradient
import android.graphics.BitmapShader
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import android.graphics.PorterDuff
import android.graphics.ComposeShader
import androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes
import com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes

class myview(context: Context, attrs: AttributeSet?, defStyleAttr:Int): View(context ,attrs,defStyleAttr){
    private var array=context.obtainStyledAttributes(attrs,R.styleable.myview)
    private var labelBool=true
    private var lineWidth=40f
    private var maxNumber=1
    private var title="标题"

    private var dataBool=true
    private var titleBool=true
    var dataList=ArrayList<dataList>()
    private var color= Color.BLUE
    private var xyBool=true
    private var paint=Paint()
    var changeNunmber=0
    fun getnumber():Int{
        return dataList.size
    }
    fun setDataList(list:List<dataList>){
        this.dataList= list as ArrayList<dataList>

    }
    fun setLabelBool(boolean: Boolean){
        this.labelBool=boolean
        invalidate()
    }
    fun setTitle(boolean: Boolean){
        this.titleBool=boolean
        invalidate()
    }
    fun setDataBool(boolean: Boolean){
        this.dataBool=boolean
        invalidate()
    }

    constructor(context: Context):this(context,null)
    constructor(context:Context,attrs: AttributeSet?):this(context,attrs,0)
    fun setList(list: ArrayList<dataList>){
        this.dataList=list
        maxNumber=list.size
        invalidate()
    }
    fun setData(int: Float){
        this.dataList[changeNunmber].number=int
        invalidate()
    }
    fun setShowNumber(int: Int){
        this.maxNumber=int
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width=MeasureSpec.getSize(widthMeasureSpec)
        val height=MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width,height)
    }
    init{
        dataList.add(dataList("西瓜",12f))
        dataList.add(dataList("红枣",11f))
        dataList.add(dataList("萝卜",14f))
        dataList.add(dataList("青菜",7f))
        dataList.add(dataList("菠萝",7f))
        maxNumber=dataList.size
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var mheight=height*1f-100f
        paint.setColor(Color.BLACK)
        paint.strokeWidth=10f
        canvas.drawLine(20f,mheight.toFloat(),20f,0f,paint)
        canvas.drawLine(20f,0f,50f,50f,paint)
        canvas.drawLine(20f,mheight.toFloat(),width.toFloat(),mheight.toFloat(),paint)
        canvas.drawLine(width.toFloat(),mheight.toFloat(),width.toFloat()-50f,mheight.toFloat()-30f,paint)
        paint.strokeWidth=50f
        paint.textSize=80f
        canvas.drawText("y",50f,70f,paint)
        canvas.drawText("x",width.toFloat()-100f,mheight.toFloat()-10,paint)
        paint.setColor(color)
        var maxHeight=0
        Log.d("11111111111111",dataList.size.toString())

        for( b in 0 until dataList.size){

            Log.d("maxHeight===",dataList[b].number.toString()+"==================================================")
            if(dataList[b].number>maxHeight){
                maxHeight=dataList[b].number.toInt()

            }
        }

        val mwidth=width/((maxNumber+1)*1f)

        var usedWith=mwidth
        paint.setStrokeWidth(40f);
        paint.setColor(color)
        paint.strokeWidth = 40.0.toFloat()
        for(i in 0 until maxNumber){
            paint.textSize=50f
            if(dataBool){
                canvas.drawText(dataList[i].number.toString(),usedWith+lineWidth/2+paint.strokeWidth/2+10f,(mheight-mheight/maxHeight*(dataList[i].number*1f))+80f,paint)
            }
            paint.textSize=30f
            if(labelBool){
                canvas.drawText(dataList[i].name,usedWith+lineWidth/2-dataList[i].name.length/2*paint.textSize,height.toFloat()-10f,paint)
            }
            if(titleBool){
                paint.textSize=40f
                canvas.drawText("标题",width-100f,50f,paint)
            }
            canvas.drawLine(usedWith+lineWidth/2,mheight.toFloat(),usedWith+lineWidth/2,(mheight-mheight/maxHeight*(dataList[i].number*1f)),paint)
            usedWith+=mwidth
        }
    }
}