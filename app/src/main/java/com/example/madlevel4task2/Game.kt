package com.example.madlevel4task2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "TableOfGames")
data class Game(
    @ColumnInfo(name = "state")
    var state: String,
    @ColumnInfo(name = "Date")
    var date: Long,
    @ColumnInfo(name = "BotMove")
    var Botmove: String,
    @ColumnInfo(name = "PlayerMove")
    var playerMove: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)