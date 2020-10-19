package com.example.madlevel4task2.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.madlevel4task2.Converters.DateConverter
import com.example.madlevel4task2.Converters.GameConverter
import com.example.madlevel4task2.Converters.MovesConverter
import com.example.madlevel4task2.Dao.GameDao
import com.example.madlevel4task2.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, MovesConverter::class, GameConverter::class)
abstract class GameListRoomDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAME_LIST_DATABASE"

        @Volatile
        private var gameListRoomDatabaseInstance: GameListRoomDatabase? = null

        fun getDatabase(context: Context): GameListRoomDatabase? {
            if (gameListRoomDatabaseInstance == null) {
                synchronized(GameListRoomDatabase::class.java) {
                    if (gameListRoomDatabaseInstance == null) {
                        gameListRoomDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                GameListRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return gameListRoomDatabaseInstance
        }
    }
}