package my.farhan.movie.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.farhan.movie.R

class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private lateinit var viewModel: MovieListVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieListVM::class.java)
        // TODO: Use the ViewModel
    }

}