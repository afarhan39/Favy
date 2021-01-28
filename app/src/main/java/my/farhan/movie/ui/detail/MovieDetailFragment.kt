package my.farhan.movie.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.farhan.movie.R

class MovieDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private lateinit var viewModel: MovieDetailVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailVM::class.java)
        // TODO: Use the ViewModel
    }

}