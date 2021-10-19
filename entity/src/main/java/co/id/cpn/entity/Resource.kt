package co.id.cpn.entity

sealed class Resource<out T: Any> {
    data class Loading<T : Any>(val data: Nothing? = null) : Resource<T>()
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Failed(val code: Int, val message: String): Resource<Nothing>()
    data class Error(val code: Int, val throwable: Throwable): Resource<Nothing>()
    object Empty: Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failed -> "Failed[message=$message]"
            is Error -> "Error[exception=${throwable.localizedMessage}]"
            is Empty -> "Empty"
            is Loading<T> -> "Loading"
        }
    }
}

