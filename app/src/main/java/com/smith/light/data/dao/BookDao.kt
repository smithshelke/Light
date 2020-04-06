package com.smith.light.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.smith.light.data.entities.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM Books")
    fun findAll(): List<Book>

    @Query("SELECT * FROM Books WHERE book_name = :bookName")
    fun findByName(bookName: String): LiveData<Book>

    @Query("SELECT * FROM Books WHERE testament = :testament")
    fun findByTestament(testament: String) : LiveData<List<Book>>

    @Query("SELECT * FROM Books WHERE book_id = :id")
    fun findById(id: Int) : LiveData<Book>
}
