package com.smith.light.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Verse::class)
@Entity(tableName = "VerseFts")
data class VerseFts(
    @ColumnInfo(name = "verse") val verse: String
)
