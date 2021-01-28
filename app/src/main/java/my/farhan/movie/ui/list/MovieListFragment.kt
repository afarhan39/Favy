package my.farhan.movie.ui.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.liaoinstan.springview.widget.SpringView
import my.farhan.movie.R
import my.farhan.movie.ui.detail.MovieDetailVM
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private val movieListVM by viewModel<MovieListVM>()
    private lateinit var svContainer: SpringView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie_list, container, false)
        svContainer = root.findViewById(R.id.svContainer)
        svContainer.setListener(object : SpringView.OnFreshListener {
            override fun onRefresh() {
                Handler(Looper.getMainLooper()).postDelayed({ svContainer.onFinishFreshAndLoad() }, 1000)
            }

            override fun onLoadmore() {
                Handler(Looper.getMainLooper()).postDelayed({ svContainer.onFinishFreshAndLoad() }, 1000)
            }
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}