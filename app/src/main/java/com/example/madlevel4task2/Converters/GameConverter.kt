package com.example.madlevel4task2.Converters

import androidx.room.TypeConverter
import com.example.madlevel4task2.Moves.GameState


class GameConverter {
    @TypeConverter
    fun gameToString(state: GameState?): String? {
        when (state) {
            GameState.DRAW -> return GameState.DRAW.toString()
            GameState.LOSE -> return GameState.LOSE.toString()
        }

        return GameState.WIN.toString()
    }

    @TypeConverter
    fun stringToGame(stringValue: String?): GameState? {
        when (stringValue) {
            GameState.DRAW.toString() -> return GameState.DRAW
            GameState.LOSE.toString() -> return GameState.LOSE
        }

        return GameState.WIN
    }
}