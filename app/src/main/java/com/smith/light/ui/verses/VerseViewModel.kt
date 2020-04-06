package com.smith.light.ui.verses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.smith.light.data.AppDatabase
import com.smith.light.data.BibleRepository
import com.smith.light.data.entities.Verse
import com.smith.light.data.entities.VerseFts

class VerseViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: BibleRepository
    // LiveData gives us updated words when they change.

    init {
        val bookDao = AppDatabase.getDatabase(application).bookDao()
        val verseDao = AppDatabase.getDatabase(application).verseDao()
        repository = BibleRepository(bookDao, verseDao)
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun getVerseByBook(bookName: String): LiveData<PagedList<Verse>> {
        val config = PagedList.Config.Builder()
            .setPageSize(60)
            .build()
        return LivePagedListBuilder(repository.getVerseByBook(bookName), config).build()
    }

    fun searchVerse(searchTerm: String): LiveData<PagedList<Verse>> {
        val config = PagedList.Config.Builder()
            .setPageSize(60)
            .build()
        return LivePagedListBuilder(repository.searchVerse(searchTerm), config).build()
    }

    fun getNumberOfChapters(bookName: String): LiveData<Int> {
        return repository.getNumberOfChapters(bookName)
    }
}
