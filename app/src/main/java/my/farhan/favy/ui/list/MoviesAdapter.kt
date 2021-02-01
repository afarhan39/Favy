package my.farhan.favy.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import my.farhan.favy.data.db.Movie
import my.farhan.favy.databinding.ItemMovieBinding

class MoviesAdapter(
    val listener: Listener
) :
    ListAdapter<Movie, MoviesAdapter.HeaderVH>(Companion) {

    companion object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.movieId == newItem.movieId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderVH {
        return HeaderVH.from(parent)
    }

    override fun onBindViewHolder(holder: HeaderVH, position: Int) {
        val item = getItem(position)
        holder.bv.movie = item
        holder.bv.listener = listener
    }

    interface Listener {
        fun onClickMovie(movieId: Int)
    }

    class HeaderVH private constructor(val bv: ItemMovieBinding) :
        RecyclerView.ViewHolder(bv.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bv = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return HeaderVH(bv)
            }
        }
    }
}