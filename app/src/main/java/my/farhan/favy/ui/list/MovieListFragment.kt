package my.farhan.favy.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liaoinstan.springview.container.AutoFooter
import com.liaoinstan.springview.widget.SpringView
import my.farhan.favy.R
import my.farhan.favy.data.SortMethod
import my.farhan.favy.data.network.Status
import my.farhan.favy.databinding.FragmentMovieListBinding
import my.farhan.favy.util.SpacesItemDecoration
import my.farhan.favy.util.TAG
import my.farhan.favy.util.observeOnceNonNull
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(), MoviesAdapter.Listener {
    private val movieListVM by viewModel<MovieListVM>()
    private lateinit var bv: FragmentMovieListBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var sortPopup: PopupMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bv = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        bv.lifecycleOwner = this
        Log.d(TAG, "onCreateView")
        return bv.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bv.vm = movieListVM
        movieListVM.sortBy(SortMethod.ReleaseDate)
        bv.fragment = this
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
        movieListVM.selectedMovie.observeOnceNonNull(viewLifecycleOwner, {
            if (it.hasCalledDetailApi) {
                findNavController().navigate(R.id.actListToDetail)
            }
        })
    }

    private fun initPopup() {
        sortPopup = PopupMenu(requireContext(), bv.clSort)
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
        Log.d(TAG, "setAdapter")
        moviesAdapter = MoviesAdapter(this)
        bv.rvMovies.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        bv.rvMovies.adapter = moviesAdapter
        val decoration = SpacesItemDecoration(16)
        bv.rvMovies.itemAnimator = DefaultItemAnimator()
        bv.rvMovies.addItemDecoration(decoration)
        movieListVM.movies.observe(viewLifecycleOwner, {
            Log.d(TAG, "moviesNeo: ${it.size}")
            if (it.isNotEmpty() && it != null) {
                moviesAdapter.submitList(it)
            }
        })
        movieListVM.apiEvent.observe(viewLifecycleOwner, {
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