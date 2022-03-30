import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.five_three.R

class TheFirstView(context: Context,attr:
AttributeSet?,defStyleAttr:Int):View(context,attr,defStyleAttr) {
    //设置画笔区域
    private var paintBar:Paint//图标画笔
    private var paintLine:Paint//线画笔
    private var paintText:Paint//文字画笔
    //颜色数组
    private var colors=ArrayList<Int>()
    //间距和宽度
    private val keduTextSpace=10//刻度与文字之间的间距
    private val keduWidth=20//坐标轴上横向标识线宽度
    private val keduSpace=100 //每个刻度之间的间距px
    private val itemSpace=60//柱状条之间的间距
    private val itemWidth=100//柱状条的宽度
    //刻度递增的值
    private var valueSpace=40
    //是否要展示柱状条对应
    private var startX=0
    private var startY=0
    private var TextSize=50
    private var maxTextWidth=0
    private var maxTextHeight=0
    private var XMaxTextRect:Rect
    private var YMaxTextRect:Rect
    //是否要展示柱状条对应的值
    private var isShowValueText=true
    //数据值
    private var Data=ArrayList<Int>()//数据
    private var yAxisList=ArrayList<Int>()//纵坐标显示数值
    private var xAxisList=ArrayList<String>()//横坐标显示名称
    //构造器（基本上不变）
    constructor(context: Context):this(context, null)
    constructor(context: Context, attributeSet:
    AttributeSet?):this(context,attributeSet,0)
    init {
        val array=context.obtainStyledAttributes(attr,R.styleable.TheFirstView)
        colors= arrayListOf(ContextCompat.getColor(context, R.color.purple_700),
            ContextCompat.getColor(context, R.color.teal_700),
            ContextCompat.getColor(context, R.color.purple_200),
            ContextCompat.getColor(context, R.color.purple_500),
            ContextCompat.getColor(context,R.color.teal_200))
        paintBar=Paint()//图标画笔
        paintLine=Paint()//线画笔
        paintText=Paint()//文字画笔
        XMaxTextRect= Rect()
        YMaxTextRect= Rect()
        TextSize= array.getDimension(R.styleable.TheFirstView_text_size,
            TextSize.toFloat()).toInt()
        initPaint(false)
//注意资源回收
        array.recycle()
    }
    //初始化画笔
    fun initPaint(isUpdate:Boolean) {
        if (!isUpdate) initData()
        val paintBGBlur=BlurMaskFilter(1F,BlurMaskFilter.Blur.INNER)//设置边缘效果
//绘制柱状图的画笔
        paintBar.style=Paint.Style.FILL
        paintBar.strokeWidth= 4F
        paintBar.maskFilter=paintBGBlur
//绘制直线的画笔
        paintLine.textSize= TextSize.toFloat()
        paintLine.color=ContextCompat.getColor(context,R.color.black)
        paintLine.isAntiAlias=true
        paintLine.strokeWidth= 1F
        paintText.getTextBounds((yAxisList[yAxisList.size-1]).toString(),0,
            (yAxisList[yAxisList.size-1]).toString().length,YMaxTextRect)
        paintText.getTextBounds(xAxisList[xAxisList.size-1],0,
            xAxisList[xAxisList.size-1].length,XMaxTextRect)
//绘制的刻度文字的最大值所占的高宽
        maxTextWidth=
            if (YMaxTextRect.width() > XMaxTextRect.width())
                YMaxTextRect.width() else XMaxTextRect.width()
        maxTextHeight =
            if (YMaxTextRect.height() > XMaxTextRect.height())
                YMaxTextRect.height() else XMaxTextRect.height()
        if (yAxisList.size>=2) valueSpace=yAxisList[1]-yAxisList[0]
        startX=maxTextWidth+keduWidth+keduTextSpace
        startY=keduSpace*(yAxisList.size-1)+maxTextHeight+if (isShowValueText)
            keduTextSpace else 0
    }
    fun initData(){
        val data= arrayListOf<Int>(80,80,80,80,80)
        data.forEach {
            Data.add(it)
            yAxisList.add(0+it*valueSpace)
        }
        val Month= arrayListOf<String>("1月","2月","3月","4月","5月")
        Month.forEach {xAxisList.add(it)}
    }
    //进行测量
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode=MeasureSpec.getMode(widthMeasureSpec)
        val heightMode=MeasureSpec.getMode(heightMeasureSpec)
        var widthSize=MeasureSpec.getSize(widthMeasureSpec)
        var heightSize=MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode==MeasureSpec.AT_MOST){
            heightSize = if (keduWidth>maxTextHeight+keduTextSpace)
                (yAxisList.size-1)*keduSpace+keduWidth+maxTextHeight
            else (yAxisList.size-1)*keduSpace+
                    (maxTextHeight+keduTextSpace)+maxTextHeight
            heightSize += keduTextSpace + if (isShowValueText) keduTextSpace
            else 0
        }
        if (widthMode==MeasureSpec.AT_MOST)widthSize=startX+Data.size*itemWidth+
                (Data.size+1)*itemSpace
        Log.e("TAG", "heightSize=" + heightSize + "widthSize=" + widthSize)
        setMeasuredDimension(widthSize,heightSize)
    }
    //进行绘制
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//绘制Y轴p
        var paint=Paint()
        canvas?.drawLine(startX.toFloat(), startY.toFloat()+keduWidth,
            startX.toFloat(), (startY-(yAxisList.size-1)*1000f).toFloat(),paint)
        for (i in 0 until yAxisList.size) {
//绘制Y轴的文字
            val textRect = Rect()
            paintText.getTextBounds(
                (yAxisList[i]).toString(),
                0,
                yAxisList[i].toString().length,
                textRect
            )
            canvas!!.drawText(
                yAxisList[i].toString(),
                (startX - keduWidth - textRect.width() -
                        keduTextSpace).toFloat(),
                (startY - (i + 1) * keduSpace + keduSpace).toFloat(),
                paintText
            )
//画X轴及上方横向的刻度线
            canvas.drawLine(
                (startX - keduWidth).toFloat(),
                (startY - keduSpace * i).toFloat(),
                (startX + Data.size * itemWidth + itemSpace * (Data.size +
                        1)).toFloat(),
                (startY - keduSpace * i).toFloat(),
                paintLine
            )
        }
        for (j in 0 until xAxisList.size){
//绘制X轴文字
            paintText.textSize=TextSize.toFloat()
            val rect=Rect()
            paintLine.getTextBounds(xAxisList[j],0,xAxisList[j].length,rect)
            canvas?.drawText(xAxisList[j],
                (startX + itemSpace * (j + 1) + itemWidth * j + itemWidth / 2 -
                        rect.width() / 2).toFloat(),
                (startY + rect.height() + keduTextSpace).toFloat(), paintText)
            if (isShowValueText) {
                val rectText = Rect()
                paintText.getTextBounds(
                    Data[j].toString() + "",
                    0,
                    (Data[j].toString() + "").length,
                    rectText
                )
                var radio=keduSpace * 1.0 / valueSpace
//绘制柱状条上的值
                canvas!!.drawText(
                    Data[j].toString() + "",
                    (startX + itemSpace * (j + 1) + itemWidth * j + itemWidth /
                            2 - rectText.width() / 2).toFloat(),
                    (startY - keduTextSpace - Data[j] * (keduSpace * 1.0 /
                            valueSpace)).toFloat(),
                    paintText
                )
            }
//绘制柱状条
            paintBar.setColor(colors[j])
//(mData.get(j) * (keduSpace * 1.0 / valueSpace))：为每个柱状条所占的高度值px

            val initx = startX + itemSpace * (j + 1) + j * itemWidth
            canvas!!.drawRect(
                initx.toFloat(), (startY - Data[j] * (keduSpace * 1.0 /
                        valueSpace)).toFloat(),
                (initx + itemWidth).toFloat(), startY.toFloat(), paintBar
            )
        }
    }
    fun updateValueData(datas: ArrayList<Int>, xList: ArrayList<String>, yList:
    ArrayList<Int>) {
        Data = datas
        xAxisList = xList
        yAxisList = yList
        initPaint( true)
        invalidate()
    }
}
