package com.example.android

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(val context: Context,name:String,version:Int) :SQLiteOpenHelper(context,name,null,version){
    private val createBook="Create table BookDownload("+"author text,"+"name text,"+"picture text,"+"theText text)"

    override fun onCreate(dy: SQLiteDatabase) {
        dy.execSQL(createBook)
    }

    override fun onUpgrade(dy: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}