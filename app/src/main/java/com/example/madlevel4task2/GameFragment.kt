package com.example.madlevel4task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madlevel4task2.Moves.GameState
import com.example.madlevel4task2.Moves.Moves
import com.example.madlevel4task2.Repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class GameFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())
        getGamesStatsFromDatabase()
        initView()
    }

    private fun initView() {
        IMG_Rock.setOnClickListener { playGame(Moves.ROCK) }
        IMG_paper.setOnClickListener { playGame(Moves.PAPER) }
        IMG_Sciccors.setOnClickListener { playGame(Moves.SCISSORS) }
    }

    private fun playGame(userMove: Moves) {
        when (userMove) {
            Moves.ROCK -> IMG_Player_Hands.setImageResource(R.drawable.rock)
            Moves.PAPER -> IMG_Player_Hands.setImageResource(R.drawable.paper)
            Moves.SCISSORS -> IMG_Player_Hands.setImageResource(R.drawable.scissors)
        }

        val movesByComputer = randomMoveComputer()

        when (checkForWin(movesByComputer, userMove)) {
            GameState.WIN -> {
                TXT_Game_Result.text = getString(R.string.Title_Win)
            }
            GameState.DRAW -> {
                TXT_Game_Result.text = getString(R.string.Title_Draw)
            }
            GameState.LOSE -> {
                TXT_Game_Result.text = getString(R.string.Title_Lose)
            }
        }
    }

    private fun randomMoveComputer(): Moves {
        val randomNumber = (0 until 3).random()

        when (numberToMove(randomNumber)) {
            Moves.ROCK -> IMG_computer_Hand.setImageResource(R.drawable.rock)
            Moves.PAPER -> IMG_computer_Hand.setImageResource(R.drawable.paper)
            Moves.SCISSORS -> IMG_computer_Hand.setImageResource(R.drawable.scissors)
        }

        return numberToMove(randomNumber)
    }

    private fun numberToMove(number: Int): Moves {
        if (number == 0) {
            return Moves.ROCK
        } else if (number == 1) {
            return Moves.PAPER
        }

        return Moves.SCISSORS
    }

    private fun checkForWin(computerMove: Moves, userMove: Moves): GameState {
        if (computerMove == userMove) {
            storeGame(
                Game(
                    state = GameState.DRAW.toString(),
                    date = Date().time,
                    Botmove = computerMove.toString(),
                    playerMove = userMove.toString()
                )
            )
            return GameState.DRAW
        } else if (computerMove == Moves.ROCK && userMove == Moves.PAPER ||
            computerMove == Moves.PAPER && userMove == Moves.SCISSORS ||
            computerMove == Moves.SCISSORS && userMove == Moves.ROCK
        ) {
            storeGame(
                Game(
                    state = GameState.WIN.toString(),
                    date = Date().time,
                    Botmove = computerMove.toString(),
                    playerMove = userMove.toString()
                )
            )
            return GameState.WIN
        } else if (userMove == Moves.ROCK && computerMove == Moves.PAPER ||
            userMove == Moves.PAPER && computerMove == Moves.SCISSORS ||
            userMove == Moves.SCISSORS && computerMove == Moves.ROCK
        ) {
            storeGame(
                Game(
                    state = GameState.LOSE.toString(),
                    date = Date().time,
                    Botmove= computerMove.toString(),
                    playerMove = userMove.toString()
                )
            )
            return GameState.LOSE
        }

        return GameState.LOSE
    }

    private fun getGamesStatsFromDatabase() {
        mainScope.launch {
            val numOfWins = withContext(Dispatchers.IO) { gameRepository.getAllWins() }
            val numOfDraws = withContext(Dispatchers.IO) { gameRepository.getAllDraws() }
            val numOfLoses = withContext(Dispatchers.IO) { gameRepository.getAllLoses() }

            TXT_Wins.text = getString(R.string.Txt_Win, numOfWins)
            TXT_Draws.text = getString(R.string.Txt_Draw, numOfDraws)
            Txt_Loses.text = getString(R.string.Txt_Loses, numOfLoses)
        }
    }

    private fun storeGame(currentGame: Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(currentGame)
            }

            getGamesStatsFromDatabase()
        }
    }
}