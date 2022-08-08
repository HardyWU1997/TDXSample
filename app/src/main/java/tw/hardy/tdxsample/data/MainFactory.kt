package tw.hardy.tdxsample.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.hardy.tdxsample.data.repository.MainRepository
import tw.hardy.tdxsample.ui.MainViewModel
import java.lang.IllegalArgumentException

class MainFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}