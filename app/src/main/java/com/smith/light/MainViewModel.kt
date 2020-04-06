package com.smith.light

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.smith.light.data.AppDatabase
import com.smith.light.data.BibleRepository
import com.smith.light.data.entities.Verse
import com.smith.light.data.entities.VerseFts

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BibleRepository

    init {
        val bookDao = AppDatabase.getDatabase(application).bookDao()
        val verseDao = AppDatabase.getDatabase(application).verseDao()
        repository = BibleRepository(bookDao, verseDao)
    }

    fun searchVerse(searchTerm: String): LiveData<PagedList<Verse>> {
        val config = PagedList.Config.Builder()
            .setPageSize(60)
            .build()
        return LivePagedListBuilder(repository.searchVerse(searchTerm), config).build()
    }

}
