package my.farhan.favy.util

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/***
 * best Extensions, I can use it anywhere, and it will return class name
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

/***
 * round numbers based on [decimals] provided
 */
fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

/***
 * convert date to epoch, for easier sorting [my.farhan.favy.data.SortMethod.ReleaseDate]
 */
fun String.toEpoch(): Long {
    return try {
        val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = parser.parse(this) ?: throw Exception("Unable to parse date")
        date.time
    } catch (e: Exception) {
        Log.e(TAG, e.toString())
        0L
    }
}

/***
 * make liveData only observeOnce when not null
 * if null, it will keep observing
 * if non null, it will removeObserver
 */
fun <T> LiveData<T>.observeOnceNonNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            t ?: return
            if (t is List<*>)
                if (t.isNullOrEmpty()) return
            removeObserver(this)
        }
    })
}