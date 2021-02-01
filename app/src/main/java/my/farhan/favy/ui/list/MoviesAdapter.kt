package my.farhan.favy.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import my.farhan.favy.data.db.Movie
import my.farhan.favy.R
import my.farhan.favy.databinding.ItemMovieBinding


class MoviesAdapter(val context: Context, val listener: Listener) :
    RecyclerView.Adapter<MoviesAdapter.MovieVH>() {

    var movieList: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val bv: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie, parent, false
        )
        return MovieVH(bv)
    }


    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.onBind(position)
    }

    fun setMovies(movieList: List<Movie>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    inner class MovieVH(private val bv: ItemMovieBinding) :
        RecyclerView.ViewHolder(bv.root) {

        fun onBind(position: Int) {
            val item = movieList[position]
            bv.movie = item
            bv.listener = listener
        }
    }

    interface Listener {
        fun onClick(movie: Movie)
    }
}


