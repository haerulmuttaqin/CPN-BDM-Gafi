package co.id.cpn.bdmgafi.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.cpn.domain.main.MainUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
class SplashViewModel constructor(val useCase: MainUseCase): ViewModel() {
    fun getInfo(keyH: String, type: String) {
        viewModelScope.launch {
            useCase.getInfo(keyH, type).collect {
                Log.w("TAG", "getInfo: $it")
            }
        }
    }
}