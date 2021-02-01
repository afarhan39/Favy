package my.farhan.favy.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import my.farhan.favy.R

object BindingAdapters {
    @BindingAdapter(value = ["hideIfEmpty"])
    @JvmStatic
    fun hideIfEmpty(view: View, isEmpty: Boolean) {
        view.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    @BindingAdapter("showIfEmpty")
    @JvmStatic
    fun showIfEmpty(view: View, isEmpty: Boolean) {
        view.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        if (url.isNotEmpty())
            Glide.with(view.context).load(url).centerCrop().into(view)
        else
            view.setImageResource(R.drawable.ic_no_image)
    }

    @BindingAdapter("movieDuration")
    @JvmStatic
    fun movieDuration(view: MaterialTextView, runTime: Int) {
        if (runTime == 0)
            view.text = view.context.getString(R.string.unavailable)
        else
            view.text = view.context.getString(R.string.duration, runTime)
    }

    @BindingAdapter("voteAverageImg")
    @JvmStatic
    fun voteAverageImg(view: ImageView, voteAverage: Double) {
        when {
            voteAverage == 0.0 -> view.setImageResource(R.drawable.ic_star_0)
            voteAverage < 1 -> view.setImageResource(R.drawable.ic_star_0_5)
            voteAverage < 2 -> view.setImageResource(R.drawable.ic_star_1)
            voteAverage < 3 -> view.setImageResource(R.drawable.ic_star_1_5)
            voteAverage < 4 -> view.setImageResource(R.drawable.ic_star_2)
            voteAverage < 5 -> view.setImageResource(R.drawable.ic_star_2_5)
            voteAverage < 6 -> view.setImageResource(R.drawable.ic_star_3)
            voteAverage < 7 -> view.setImageResource(R.drawable.ic_star_3_5)
            voteAverage < 8 -> view.setImageResource(R.drawable.ic_star_4)
            voteAverage < 9 -> view.setImageResource(R.drawable.ic_star_4_5)
            voteAverage < 10 -> view.setImageResource(R.drawable.ic_star_5)
        }
    }

    @BindingAdapter(value = ["voteAverage", "voteCount"])
    @JvmStatic
    fun voteAverageAndCount(view: MaterialTextView, voteAverage: Double, voteCount: Int) {
        view.text = "${voteAverage.round(1)}($voteCount)"
    }

    @BindingAdapter("genre")
    @JvmStatic
    fun genre(view: MaterialTextView, genre: List<String>) {
        view.text = genre.joinToString(separator = ", ") { it }
    }

    @BindingAdapter("popularityText")
    @JvmStatic
    fun popularityText(view: MaterialTextView, popularity: Double) {
        if (popularity <= 100)
            view.text = "${popularity.round(1)}"
        else
            view.text = "${popularity.toInt()}"
    }

    @BindingAdapter("popularityLevel")
    @JvmStatic
    fun popularityLevel(view: CircularProgressIndicator, popularity: Double) {
        view.max = 100
        if (popularity <= 100)
            view.setProgressCompat(popularity.toInt(), true)
        else
            view.setProgressCompat(100, true)
    }
}