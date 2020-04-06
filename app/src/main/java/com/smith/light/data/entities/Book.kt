package com.smith.light.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "Books",
    primaryKeys = ["book_id"],
    indices = [Index(value = ["book_name"], unique = true)]
)
data class Book(
    @ColumnInfo(name = "book_id") val bookId: Int,
    @ColumnInfo(name = "book_name") val bookName: String,
    @ColumnInfo(name = "testament") val testament: String
)
