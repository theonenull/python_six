package com.linc.game


import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.selfviewgroup.R
import com.linc.game.LevelMenuItem.OnItemClickListener

/**
 * 自定义一个关卡
 * 共有7个属性，看attr文件
 * 在程序中提供修改这7个属性的接口，
 * 一个自定义控件的任务就算完成。
 * 一个自定义控件的好处就是把一些需要模块化的
 * UI和逻辑放在一起，做到了高内聚，向其他模块提供接口并很少
 * 依赖外界，这样就是低耦合。一个自定义控件就是一个封闭的王国，
 * 这里由你掌控。
 *
 * 编写时，如果遇到在attr里写好属性，但是在这里认不出来，
 * 就clean一下项目。切记。
 *
 * @author linc
 */
class LevelMenuItem : LinearLayout {
    private var mTextView: TextView? = null
    private var mImageView: ImageView? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.level_menu_item, this)
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.LevelMenuItem
        )
        initWidget(typedArray)
    }

    private fun initWidget(typedArray: TypedArray) {
        mTextView = findViewById<View>(R.id.tv_item) as TextView
        val textString = typedArray.getString(R.styleable.LevelMenuItem_text)
        val textColor = typedArray.getColor(
            R.styleable.LevelMenuItem_text_color,
            -0x1
        )
        val textSize = typedArray.getDimension(R.styleable.LevelMenuItem_text_size, 20f)
        mTextView!!.text = textString
        mTextView!!.setTextColor(textColor)
        mTextView!!.textSize = textSize
        mImageView = findViewById<View>(R.id.image_item) as ImageView
        val imageHeight =
            typedArray.getDimension(R.styleable.LevelMenuItem_image_height, 25f).toInt()
        val imageWidth =
            typedArray.getDimension(R.styleable.LevelMenuItem_image_width, 25f).toInt()
        val imageSrc = typedArray.getResourceId(R.styleable.LevelMenuItem_image_src, 0)
        val imageBg = typedArray.getResourceId(R.styleable.LevelMenuItem_image_bg, 0)
        val imageAlpha = typedArray.getInt(R.styleable.LevelMenuItem_image_alpha, 255)
        mImageView!!.setAlpha(imageAlpha)
        mImageView!!.setImageResource(imageSrc)
        mImageView!!.setBackgroundResource(imageBg)
        val layoutParams = LayoutParams(imageWidth, imageHeight)
        mImageView!!.layoutParams = layoutParams
        typedArray.recycle()
    }

    /**
     * 设置此控件的文本
     * @param text
     */
    fun setText(text: String?) {
        mTextView!!.text = text
    }

    /**
     * 设置文字颜色
     * @param textColor
     */
    fun setTextColor(textColor: Int) {
        mTextView!!.setTextColor(textColor)
    }

    /**
     * 设置字体大小
     * @param textSize
     */
    fun setTextSize(textSize: Int) {
        mTextView!!.textSize = textSize.toFloat()
    }

    /**
     * 设置图片
     * @param resId
     */
    fun setImageResource(resId: Int) {
        mImageView!!.setImageResource(resId)
    }

    /**
     * 设置图片背景
     */
    override fun setBackgroundResource(resId: Int) {
        mImageView!!.setBackgroundResource(resId)
    }

    /**
     * 设置图片的不透名度
     * @param alpha
     */
    fun setImageAlpha(alpha: Int) {
        mImageView!!.setAlpha(alpha)
    }

    /**
     * 设置图片的大小
     * 这里面需要使用LayoutParams这个布局参数来设置
     * @param width
     * @param height
     */
    fun setImageSize(width: Int, height: Int) {
        val layoutParams = LayoutParams(width, height)
        mImageView!!.layoutParams = layoutParams
    }

    /**
     * image点击事件的回调
     * @param listener
     */
    fun setOnClickListener(listener: OnItemClickListener) {
        mImageView!!.setOnClickListener { listener.onImageClick() }
    }

    /**
     * 点击事件接口
     * @author linc
     */
    interface OnItemClickListener {
        fun onImageClick()
    }
}