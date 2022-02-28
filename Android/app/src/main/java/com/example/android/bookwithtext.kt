package com.example.android
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

//@Entity
data class
bookwithtext (val bookName:String,val bookAuthor:String,val bookPhoto: String,val bookIntroduction:String,val context:String){
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
    var text:String=""
}
