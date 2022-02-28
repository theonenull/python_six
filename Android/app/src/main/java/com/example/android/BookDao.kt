package com.example.android

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(onebook:book):Long

    @Delete
    fun deletebook(bookname:book)

    @Query("select * from book")
    fun loadAllBooks():List<book>
}