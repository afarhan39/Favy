package my.farhan.movie.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import my.farhan.movie.R
import my.farhan.movie.databinding.FragmentMovieDetailBinding
import my.farhan.movie.ui.list.MovieListVM
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {
    private val movieDetailVM by viewModel<MovieDetailVM>()
    private lateinit var bv: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bv = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        bv.lifecycleOwner = this
        return bv.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bv.vm = movieDetailVM
    }
}