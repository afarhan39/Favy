package my.farhan.favy.data.network

data class ApiEvent(val status: Status, val message: String)

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}