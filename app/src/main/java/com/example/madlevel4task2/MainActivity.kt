package com.example.madlevel4task2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.madlevel4task2.Repository.GameRepository
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var historyButton: MenuItem
    private lateinit var deleteButton: MenuItem
    private lateinit var gameRepository: GameRepository
    private lateinit var gameAdapter: GameAdapter
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        gameRepository = GameRepository(baseContext)

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            historyButton = menu.findItem(R.id.Btn_History)
            deleteButton = menu.findItem(R.id.Btn_Delete)
            deleteButton.isVisible = false
        }
        toggleNavIcon()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.Btn_Delete -> {
                deleteAllGames()
                true
            }
            R.id.Btn_History -> {
                navController.navigate(R.id.action_GameFragment_to_HistoryFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportActionBar?.setHomeButtonEnabled(false)
            supportFragmentManager.popBackStack()
        }
        return true
    }

    private fun deleteAllGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            gameAdapter = GameAdapter(arrayListOf())
            rvGames.adapter = gameAdapter
            gameAdapter.notifyDataSetChanged()

            Toast.makeText(
                baseContext,
                "Successful deleted all games",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun toggleNavIcon() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.historyFragment)) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setTitle(R.string.Title_History)
                historyButton.isVisible = false
                deleteButton.isVisible = true
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setTitle(R.string.app_name)
                historyButton.isVisible = true
                deleteButton.isVisible = false
            }
        }
    }

}