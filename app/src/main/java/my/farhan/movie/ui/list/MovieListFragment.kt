package my.farhan.movie.ui.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liaoinstan.springview.widget.SpringView
import my.farhan.movie.R
import my.farhan.movie.data.db.Movie
import my.farhan.movie.databinding.FragmentMovieListBinding
import my.farhan.movie.util.TAG
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(), MoviesAdapter.Listener {
    private val movieVM by viewModel<MovieListVM>()
    private lateinit var bv: FragmentMovieListBinding
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bv = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        bv.lifecycleOwner = this
        return bv.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bv.vm = movieVM

        bv.svContainer.setListener(object : SpringView.OnFreshListener {
            override fun onRefresh() {
                bv.vm!!.onLoadMovie()
                Handler(Looper.getMainLooper()).postDelayed({ bv.svContainer.onFinishFreshAndLoad() }, 1000)
            }

            override fun onLoadmore() {
                Handler(Looper.getMainLooper()).postDelayed({ bv.svContainer.onFinishFreshAndLoad() }, 1000)
            }
        })

        moviesAdapter = MoviesAdapter(requireContext(), this)
        bv.rvMovies.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        bv.rvMovies.adapter = moviesAdapter
        movieVM.movieList.observe(viewLifecycleOwner, {
            if (it.isNotEmpty() && it != null) {
                moviesAdapter.setMovies(it)
            }
        })
    }

    override fun onClick(movie: Movie) {
        Log.d(TAG, movie.title)
        movieVM.selectMovie(movie)
        findNavController().navigate(R.id.actListToDetail)
    }
}