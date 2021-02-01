package my.farhan.favy.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import my.farhan.favy.R
import my.farhan.favy.databinding.FragmentMovieDetailBinding
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
        bv.fragment = this
        return bv.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bv.vm = movieDetailVM
    }

    fun bookNow() {
        val url = "https://www.cathaycineplexes.com.sg/"
        val customTabBuilder = CustomTabsIntent.Builder()
        val customTabColorBuilder = CustomTabColorSchemeParams.Builder()
        customTabColorBuilder.setToolbarColor(resources.getColor(R.color.purple_200, null))
        customTabColorBuilder.setSecondaryToolbarColor(resources.getColor(R.color.teal_200, null))
        customTabColorBuilder.setNavigationBarColor(resources.getColor(R.color.purple_700, null))
        customTabColorBuilder.setNavigationBarDividerColor(resources.getColor(R.color.black, null))
        customTabBuilder.setDefaultColorSchemeParams(customTabColorBuilder.build())
        customTabBuilder.build().launchUrl(requireContext(), Uri.parse(url))
    }
}