package tw.hardy.tdxsample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import tw.hardy.tdxsample.data.MainFactory
import tw.hardy.tdxsample.data.ResourceState
import tw.hardy.tdxsample.data.repository.MainRepository
import tw.hardy.tdxsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainRepository by lazy { MainRepository() }
    private val mainViewModel: MainViewModel by viewModels { MainFactory(mainRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getData()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getBusData.observe(this, { state ->
            when (state) {
                is ResourceState.Success -> {
                    binding.txt.text = state.value.toString()
                }
                is ResourceState.Failure -> binding.txt.text = state.errorBody
                ResourceState.Loading -> binding.txt.text = "讀取中..."
            }
        })
    }
}