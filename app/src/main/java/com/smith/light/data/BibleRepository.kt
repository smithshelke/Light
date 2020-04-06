package com.smith.light.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.smith.light.data.dao.BookDao
import com.smith.light.data.dao.VerseDao
import com.smith.light.data.entities.Book
import com.smith.light.data.entities.Verse
import com.smith.light.data.entities.VerseFts

class BibleRepository(private val bookDao: BookDao, private val verseDao: VerseDao) {

    suspend fun getBooksByTestament(testament: String): LiveData<List<Book>> =
        bookDao.findByTestament(testament)

    suspend fun getBookByName(name: String) {
        bookDao.findByName(name)
    }

    fun getBookByName(id: Int) {
        bookDao.findById(id)
    }

     fun getVerseByBook(bookName: String): DataSource.Factory<Int, Verse>{
        return verseDao.findByBook(bookName)
    }

    fun getVerseByChapter(bookName: String, chapter: Int): LiveData<List<Verse>> {
        return verseDao.findByChapter(bookName, chapter)
    }

    fun getVerse(bookName: String, chapter: Int, verseNo: Int): LiveData<Verse> {
        return verseDao.find(bookName, chapter, verseNo)
    }

    fun searchVerse(searchTerm : String): DataSource.Factory<Int, Verse> {
        return verseDao.find(searchTerm)
    }

    fun getNumberOfChapters(bookName: String): LiveData<Int> {
        return verseDao.findNumberOfChapters(bookName)
    }
}
