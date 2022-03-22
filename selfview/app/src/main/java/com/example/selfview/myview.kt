package com.example.selfview

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


class myview(context: Context, attrs: AttributeSet?, defStyleAttr:Int) : View(context,attrs,defStyleAttr) {
    private var mOuterColor=Color.YELLOW
    private var mInnerColor=Color.BLUE
    private var mStepTextColor=Color.BLUE
    private var mStepTextSize=0
    private var mBorderWidth=0
    private var mCurrentStep:Float=5000f
    private var mpaint=Paint()
    private var ipaint=Paint()
    private var mtextpaint=Paint()
    private var array=context.obtainStyledAttributes(attrs,R.styleable.myview)
    private var rectF=RectF(0f,0f,width*1f,height*1f)

    val mstep=8000
    var mText:String=""
    constructor(context: Context) : this(context,null)
    constructor(context: Context,attributeSet: AttributeSet?):this(context,attributeSet,0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width=MeasureSpec.getSize(widthMeasureSpec)
        val height=MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(if(width<height) width else height,if(width<height) width else height)
        Log.d("onMeasure",width.toString())
        Log.d("onMeasure",height.toString())
        Log.d("onMeasure san",(if(width<height) width else height).toString())

    }

    //数据初始化
    init{
        mOuterColor=array.getColor(R.styleable.myview_outer_color,mOuterColor)
        mInnerColor=array.getColor(R.styleable.myview_inner_color,mInnerColor)
        mBorderWidth=array.getDimension(R.styleable.myview_border_width,mBorderWidth*1f).toInt()
        mStepTextColor=array.getColor(R.styleable.myview_step_text_color,mStepTextColor)
        mStepTextSize=array.getDimensionPixelSize(R.styleable.myview_step_text_size,mStepTextSize)
        mpaint.setAntiAlias(true)
        mpaint.setColor(mOuterColor)
        (mBorderWidth*1f).also { mpaint.strokeWidth = it }
        array=context.obtainStyledAttributes(attrs,R.styleable.myview)
        array.recycle()
        Log.d("2222222222222",width.toString())
        Log.d("2222222222222height",height.toString())
        mText=mCurrentStep.toString()

        mpaint=Paint()
        mpaint.setStyle(Paint.Style.STROKE); // 画线模式
        mpaint.setColor(mOuterColor)
        mpaint.setAntiAlias(true)
        mpaint.strokeWidth=mBorderWidth.toFloat()


        ipaint=Paint()
        ipaint.setStyle(Paint.Style.STROKE); // 画线模式
        ipaint.setColor(mInnerColor)
        ipaint.setAntiAlias(true)
        ipaint.strokeWidth=mBorderWidth.toFloat()

        mtextpaint.setColor(mStepTextColor)
        mtextpaint.textSize=mStepTextSize.toFloat()
        mtextpaint.isAntiAlias=true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画圆弧
        Log.d("111111111111111",width.toString())
        Log.d("111111111111111height",height.toString())

        canvas.drawArc((mBorderWidth/2).toFloat(), (mBorderWidth/2).toFloat(), (if(width<height) width else height).toFloat()-(mBorderWidth/2).toFloat(), (if(width<height) width else height).toFloat()-(mBorderWidth/2).toFloat(), 135f, 270f, false, mpaint); // 绘制弧形
        canvas.drawArc((mBorderWidth/2).toFloat(), (mBorderWidth/2).toFloat(),(if(width<height) width else height).toFloat()-(mBorderWidth/2).toFloat(), (if(width<height) width else height).toFloat()-(mBorderWidth/2).toFloat(), 135f,270*mCurrentStep/mstep.toFloat(),false,ipaint)
        var fontMetricsInt=ipaint.fontMetricsInt
        var dy=fontMetricsInt.bottom-fontMetricsInt.top
        var baseline=(height/2-dy/2)/2

        var rect=Rect()
        mtextpaint.getTextBounds(mText,0,mText.length,rect)
        var dx=width/2-rect.width()/2
        canvas.drawText(mText,dx.toFloat(),baseline.toFloat(),mtextpaint)
    }
    fun setMCurrentStep(float: Float){
        this.mCurrentStep=float
        this.mText=mCurrentStep.toString()
        this.invalidate()
    }
}