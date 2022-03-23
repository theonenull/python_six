package com.linc.game


import android.app.ProgressDialog.show
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.selfviewgroup.R

class LevelMenu : LinearLayout {
    private var item1: LevelMenuItem? = null
    private var item2: LevelMenuItem? = null
    private var item3: LevelMenuItem? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.level_menu_item, this)
        initWidget()
    }

    private fun initWidget() {
        item1 = findViewById<View>(R.id.item1) as LevelMenuItem
        item2 = findViewById<View>(R.id.item2) as LevelMenuItem
        item3 = findViewById<View>(R.id.item3) as LevelMenuItem
        item1!!.setOnClickListener(object : LevelMenuItem.OnItemClickListener {
            override fun onImageClick() {
                Toast.makeText(context,"wwwwwwwwwwwww",Toast.LENGTH_SHORT).show()

            }
        })
    }
}