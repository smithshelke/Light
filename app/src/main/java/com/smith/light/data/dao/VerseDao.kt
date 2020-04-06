package com.smith.light.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.smith.light.data.entities.Verse
import com.smith.light.data.entities.VerseFts

@Dao
interface VerseDao {

    @Query("SELECT * FROM Verse WHERE book_name = :bookName")
    fun findByBook(bookName: String): DataSource.Factory<Int, Verse>

    @Query("SELECT * FROM Verse WHERE chapter = :chapter AND book_name = :bookName")
    fun findByChapter(bookName: String, chapter: Int): LiveData<List<Verse>>

    @Query("SELECT * FROM Verse WHERE chapter = :chapter AND verse_no = :verseNo AND book_name = :bookName")
    fun find(bookName: String, chapter: Int, verseNo: Int): LiveData<Verse>

    @Query("SELECT chapter from verse where book_name = :bookName ORDER by  chapter desc  LIMIT 1")
    fun findNumberOfChapters(bookName: String): LiveData<Int>

    @Query("SELECT * FROM Verse JOIN VerseFts ON (Verse.id = VerseFts.docid) WHERE VerseFts MATCH :term")
    fun find(term : String) : DataSource.Factory<Int, Verse>

}
