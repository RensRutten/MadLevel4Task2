package com.example.madlevel4task2.Repository

import android.content.Context
import com.example.madlevel4task2.Dao.GameDao
import com.example.madlevel4task2.Database.GameListRoomDatabase
import com.example.madlevel4task2.Game
import com.example.madlevel4task2.Moves.GameState


class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = GameListRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.allGames()
    }

    suspend fun insertGame(product: Game) {
        gameDao.insertGame(product)
    }

    suspend fun deleteGame(product: Game) {
        gameDao.deleteGame(product)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun getAllWins(): Int {
        return gameDao.getGamesTotal(GameState.WIN)
    }

    suspend fun getAllDraws(): Int {
        return gameDao.getGamesTotal(GameState.DRAW)
    }

    suspend fun getAllLoses(): Int {
        return gameDao.getGamesTotal(GameState.LOSE)
    }
}