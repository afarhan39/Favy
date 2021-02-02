package my.farhan.favy.ui.detail

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import my.farhan.favy.R
import my.farhan.favy.databinding.ActivityMovieDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {
    private val movieDetailVM by viewModel<MovieDetailVM>()
    private lateinit var bv: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        bv.vm = movieDetailVM
        bv.activity = this
        bv.lifecycleOwner = this
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
        customTabBuilder.build().launchUrl(this, Uri.parse(url))
    }
}