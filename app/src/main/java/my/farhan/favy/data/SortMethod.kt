package my.farhan.favy.data

/***
 * An enum class that defined SortMethod.
 * [ReleaseDate] is set by default
 */
enum class SortMethod(val label: String) {
    ReleaseDate("Release Date"),
    Alphabetical("Alphabetical"),
    Rating("Rating"),
    Popularity("Popularity");

    companion object {
        private val map = values().associateBy { it.label }
        fun fromLabel(label: String): SortMethod = map[label] ?: ReleaseDate
    }
}