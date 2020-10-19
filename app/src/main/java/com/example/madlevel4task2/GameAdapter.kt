package com.example.madlevel4task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.Converters.DateConverter
import com.example.madlevel4task2.Converters.GameConverter
import com.example.madlevel4task2.Moves.Moves
import kotlinx.android.synthetic.main.item_game.view.*


class GameAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun dataBind(game: Game) {
            itemView.Txt_status.text =
                GameConverter().stringToGame(game.state).toString()
            itemView.Txt_Datum.text = DateConverter().fromTime(game.date).toString()
            itemView.IMG_comp.setImageResource(movesToImgConverter(game.Botmove))
            itemView.IMG_You.setImageResource(movesToImgConverter(game.playerMove))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

    private fun movesToImgConverter(move: String): Int {
        when (move) {
            Moves.ROCK.toString() -> return R.drawable.rock
            Moves.PAPER.toString() -> return R.drawable.paper
        }

        return R.drawable.scissors
    }
}