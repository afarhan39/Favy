package my.farhan.favy.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import my.farhan.favy.R

/***
 * Personal best class by far, as it provide bridge between XML and data
 */
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

    /***
     * in the event if [url] is empty, it will load [R.drawable.ic_no_image]
     * [url] is referring to
     * [my.farhan.favy.data.db.Movie.posterUrl] and
     * [my.farhan.favy.data.db.Movie.backDropUrl] and
     */
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

    /***
     * setImageResource to all different vector of rating according to [voteAverage]
     */
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

    /***
     * to prevent text overflow, [popularity] will be set to INT if exceed 100
     */
    @BindingAdapter("popularityText")
    @JvmStatic
    fun popularityText(view: MaterialTextView, popularity: Double) {
        if (popularity <= 100)
            view.text = "${popularity.round(1)}"
        else
            view.text = "${popularity.toInt()}"
    }

    /***
     * [popularity] is strange thing, as it seems it does not have upper limit
     * thus I set it to 100, and if [popularity] exceeds it, it will shown as max popularity
     */
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