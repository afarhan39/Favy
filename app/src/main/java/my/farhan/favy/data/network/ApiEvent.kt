package my.farhan.favy.data.network

/***
 * A data class to be used with MutableLiveData to observe API status
 */
data class ApiEvent(val status: Status, val message: String)

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}