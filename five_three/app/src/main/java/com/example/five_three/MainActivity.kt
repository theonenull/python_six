package com.example.five_three

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main)
        var list=ArrayList<dataList>()
        list.add(dataList("西瓜",12f))
        list.add(dataList("苹果",11f))
        list.add(dataList("苦瓜",4f))
        list.add(dataList("大白菜",7f))
        list.add(dataList("西红柿",7f))
        list.add(dataList("香蕉",7f))
        list.add(dataList("水蜜桃",7f))
        var button: Button =findViewById(R.id.button)
        var view:myview=findViewById(R.id.name)
        button.setOnClickListener {
            view.setList(list)
        }
        var lableBoolean=false
        var dataBoolean=false
        var titleBoolean=false
        var checkBox:CheckBox=findViewById(R.id.checkBox)
        var checkBox2:CheckBox=findViewById(R.id.checkBox2)
        var checkBox3:CheckBox=findViewById(R.id.checkBox3)
        var editText:EditText=findViewById(R.id.editText)
        var editText2:EditText=findViewById(R.id.editText2)
        var editText3:EditText=findViewById(R.id.editText3)
        checkBox.setOnClickListener{
            view.setTitle(titleBoolean)
            titleBoolean=!titleBoolean
        }
        checkBox2.setOnClickListener{
            view.setDataBool(dataBoolean)
            dataBoolean=!dataBoolean
        }
        checkBox3.setOnClickListener{
            view.setLabelBool(lableBoolean)
            lableBoolean=!lableBoolean
        }
        var showNumber:Button=findViewById(R.id.shownumber)
        showNumber.setOnClickListener {
            try{
                var maxShowNumber=editText3.text.toString().toInt()
                if(maxShowNumber>0 && maxShowNumber<=view.getnumber()){
                    view.setShowNumber(maxShowNumber)
                }
                else {
                    Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception){
                Toast.makeText(this,"输入有误",Toast.LENGTH_SHORT).show()
            }
        }
        var button2:Button=findViewById(R.id.button2)
        button2.setOnClickListener {
            try{
                var number=editText.text.toString().toInt()
                var data=editText2.text.toString().toInt()

                if(number-1>=0 && number-1<=view.getnumber()){
                    view.changeNunmber=number-1
                    val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                        view, "data", 0f,data.toFloat()
                    )
                    animator.interpolator = OvershootInterpolator()
                    animator.setDuration(2000)
                    animator.start()
                }
                else{
                    Toast.makeText(this,"输入有误",Toast.LENGTH_SHORT).show()
                }
                var maxShowNumber=editText3.text
            }
            catch (e:Exception){
                Toast.makeText(this,"输入有误",Toast.LENGTH_SHORT).show()
            }

        }

    }
}