package com.example.madlevel4task2.Converters

import androidx.room.TypeConverter
import com.example.madlevel4task2.Moves.Moves

/**
 * This class will convert the Moves to String values
 */
class MovesConverter {
    @TypeConverter
    fun fromMoveToString(move: Moves?): String? {
        when (move) {
            Moves.ROCK -> return Moves.ROCK.toString()
            Moves.PAPER -> return Moves.PAPER.toString()
        }

        return Moves.SCISSORS.toString()
    }

    @TypeConverter
    fun stringToMove(stringValue: String?): Moves? {
        when (stringValue) {
            Moves.ROCK.toString() -> return Moves.ROCK
            Moves.PAPER.toString() -> return Moves.PAPER
        }

        return Moves.SCISSORS
    }
}