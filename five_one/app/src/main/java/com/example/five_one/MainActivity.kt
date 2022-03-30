package com.example.five_one

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    var onLight=false
    var onTitle=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar: Toolbar =findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        toolbar.setTitle("另一个标题")
        var view:CustomTitleBar=findViewById(R.id.myview)
//        var button: Button =findViewById(R.id.button)
//        button.setOnClickListener {
//            toolbar.title = "大标题"
//            Toast.makeText(this,"you clicked button",Toast.LENGTH_SHORT).show()
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){

            R.id.backup-> {
                val view:CustomTitleBar=this.findViewById(R.id.myview)
                view.setLight(this.onLight)
                this.onLight=!this.onLight
            }
            R.id.enter-> {
                val view:CustomTitleBar=this.findViewById(R.id.myview)
                var toolbar: Toolbar =findViewById(R.id.toolbar2)
                if(view.getTitle()!=""){
                    toolbar.setTitle(view.getTitle())
                    Toast.makeText(this, "更换标题", Toast.LENGTH_SHORT).show()
                }
                else if(!onTitle){
                    toolbar.setTitle("另一个标题")
                    onTitle=!onTitle
                    Toast.makeText(this, "更换标题为默认", Toast.LENGTH_SHORT).show()
                }
                else{
                    toolbar.setTitle("Android")
                    onTitle=!onTitle
                    Toast.makeText(this, "更换标题为另一个默认", Toast.LENGTH_SHORT).show()
                }



            }
            R.id.delete-> {
                val view:CustomTitleBar=this.findViewById(R.id.myview)
                view.clearText()
                Toast.makeText(this, "删除文本", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}