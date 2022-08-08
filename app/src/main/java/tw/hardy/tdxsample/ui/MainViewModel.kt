package tw.hardy.tdxsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.hardy.tdxsample.data.GetBusOperatorResponse
import tw.hardy.tdxsample.data.ResourceState
import tw.hardy.tdxsample.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _getBusData: MutableLiveData<ResourceState<List<GetBusOperatorResponse>>> =
        MutableLiveData()

    val getBusData: LiveData<ResourceState<List<GetBusOperatorResponse>>> get() = _getBusData

    /**
     * (1)取得TDX token
     * (2)取得你想取得的資料
     */
    fun getData() = viewModelScope.launch {
        when (val tokenResult = mainRepository.getToken()) {
            is ResourceState.Success -> {
                tokenResult.value.access_token?.let { token ->
                    val tokenFormat = "${tokenResult.value.token_type} $token"
                    getBusOperator(tokenFormat) // 取得指定縣市市區公車營運業者資料
                } ?: run {
                    _getBusData.value = ResourceState.Failure("TOKEN NULL！")
                }
            }
            is ResourceState.Failure -> {
                _getBusData.value = ResourceState.Failure("取得token失敗：${tokenResult.errorBody}")
            }
            ResourceState.Loading -> _getBusData.value = ResourceState.Loading
        }
    }

    /**
     * 取得指定縣市的市區公車營運業者資料
     */
    private fun getBusOperator(token: String) = viewModelScope.launch {
        when (val getBusOperationResult = mainRepository.getBusOperator(token)) {
            is ResourceState.Success -> {
                _getBusData.value = getBusOperationResult
            }
            is ResourceState.Failure -> {
                _getBusData.value =
                    ResourceState.Failure("取得業者資料失敗：${getBusOperationResult.errorBody}")
            }
            ResourceState.Loading -> _getBusData.value = ResourceState.Loading
        }
    }

}