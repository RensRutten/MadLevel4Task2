package com.example.madlevel4task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madlevel4task2.Repository.GameRepository
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private lateinit var gameAdapter: GameAdapter
    private var gameList: ArrayList<Game> = arrayListOf()

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        initFragment()
    }

    private fun initFragment() {
        gameAdapter = GameAdapter(gameList)
        rvGames.adapter = gameAdapter
        rvGames.layoutManager = LinearLayoutManager(activity)
        getListFromDatabase()

        rvGames.addItemDecoration(
            DividerItemDecoration(
                activity, DividerItemDecoration.VERTICAL
            )
        )


    }


    private fun getListFromDatabase() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) { // retrieve stored games
                gameRepository.getAllGames()
            }

            this@HistoryFragment.gameList.clear() // clear local list
            this@HistoryFragment.gameList.addAll(games) // fill local list
            this@HistoryFragment.gameAdapter.notifyDataSetChanged() // refresh list
        }
    }
}