package com.example.madlevel4task2.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel4task2.Game
import com.example.madlevel4task2.Moves.GameState

@Dao
interface GameDao {
    @Query("SELECT * FROM TableOfGames")
    suspend fun allGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM TableOfGames")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM TableOfGames WHERE state LIKE :gameState")
    suspend fun getGamesTotal(gameState: GameState): Int
    fun allGames(p0: Continuation<in List<Game>>): Any
}