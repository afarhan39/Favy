package my.farhan.favy.util

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun String.toEpoch(): Long {
    return try {
        val parser: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = parser.parse(this)?: throw Exception("Unable to parse date")
        date.time
    } catch (e: Exception) {
        Log.e(TAG, e.toString())
        0L
    }
}