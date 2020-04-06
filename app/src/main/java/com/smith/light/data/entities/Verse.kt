package com.smith.light.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Verse",
    primaryKeys = ["id"]
)
data class Verse(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "book_name") val bookName: String,
    @ColumnInfo(name = "chapter") val chapter: Int,
    @ColumnInfo(name = "verse_no") val verseNo: Int,
    @ColumnInfo(name = "verse") val verse: String
)
