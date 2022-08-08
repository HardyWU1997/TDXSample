package tw.hardy.tdxsample.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException

sealed class ResourceState<out T> {
    data class Success<out T>(val value: T) : ResourceState<T>()
    data class Failure(val errorBody: String?) : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()
}

interface ApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResourceState<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResourceState.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> ResourceState.Failure(
                        throwable.response()?.errorBody()?.string().toString()
                    )
                    is ConnectException -> ResourceState.Failure("無法連線，請確認網路狀態")
                    else -> ResourceState.Failure(throwable.message)
                }
            }
        }
    }
}