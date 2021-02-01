package my.farhan.favy.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liaoinstan.springview.container.AutoFooter
import com.liaoinstan.springview.widget.SpringView
import my.farhan.favy.R
import my.farhan.favy.data.SortMethod
import my.farhan.favy.data.network.Status
import my.farhan.favy.databinding.ActivityMovieListBinding
import my.farhan.favy.ui.detail.MovieDetailActivity
import my.farhan.favy.util.SpacesItemDecoration
import my.farhan.favy.util.TAG
import my.farhan.favy.util.observeOnceNonNull
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListActivity : AppCompatActivity(), MoviesAdapter.Listener {
    private val movieListVM by viewModel<MovieListVM>()
    private lateinit var bv: ActivityMovieListBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var sortPopup: PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        bv.vm = movieListVM
        bv.activity = this
        bv.lifecycleOwner = this

        movieListVM.sortBy(SortMethod.ReleaseDate)
        initPopup()
        setAdapter()
        bv.svContainer.setListener(object : SpringView.OnFreshListener {
            override fun onRefresh() {
                movieListVM.onRefreshMovies()
            }

            override fun onLoadmore() {
                movieListVM.onLoadMoreMovies()
            }
        })
        bv.svContainer.footer = AutoFooter()
    }

    override fun onClickMovie(movieId: Int) {
        movieListVM.getMovieDetails(movieId)
        movieListVM.selectedMovie.observeOnceNonNull(this, {
            startActivity(Intent(this, MovieDetailActivity::class.java))
        })
    }

    private fun initPopup() {
        sortPopup = PopupMenu(this, bv.clSort)
        for (item in movieListVM.sortOptionList)
            sortPopup.menu.add(item)

        sortPopup.setOnMenuItemClickListener { menuItem: MenuItem ->
            val sortMethod = SortMethod.fromLabel(menuItem.toString())
            movieListVM.sortBy(sortMethod)
            true
        }
    }

    fun showSortPopup() {
        sortPopup.show()
    }

    private fun setAdapter() {
        moviesAdapter = MoviesAdapter(this)
        bv.rvMovies.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        bv.rvMovies.adapter = moviesAdapter
        val decoration = SpacesItemDecoration(16)
        bv.rvMovies.itemAnimator = DefaultItemAnimator()
        bv.rvMovies.addItemDecoration(decoration)
        movieListVM.movies.observe(this, {
            if (it.isNotEmpty() && it != null) {
                moviesAdapter.submitList(it)
            }
        })
        movieListVM.apiEvent.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    bv.svContainer.onFinishFreshAndLoad()
                }
                Status.ERROR -> {
                    bv.svContainer.onFinishFreshAndLoad()
                }
                Status.LOADING -> {
                }
            }
        })
    }
}