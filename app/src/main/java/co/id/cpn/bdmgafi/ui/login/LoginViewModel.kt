package co.id.cpn.bdmgafi.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.cpn.domain.distribution.DistUseCase
import co.id.cpn.domain.main.MainUseCase
import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Region
import co.id.cpn.entity.Resource
import co.id.cpn.entity.login.ListDistributionItem
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import co.id.cpn.entity.util.SingleEvent
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val mainUseCase: MainUseCase,
    private val distUseCase: DistUseCase
) : ViewModel() {

    private val _loginLiveData = MutableLiveData<Resource<DataBody<LoginResponse>>>()
    val loginLiveData: LiveData<Resource<DataBody<LoginResponse>>> get() = _loginLiveData

    private val _token = MutableLiveData<Resource<DataBody<JsonObject>>>()
    val token: LiveData<Resource<DataBody<JsonObject>>> get() = _token

    private val _errorMessage = MutableLiveData<SingleEvent<Any>>()
    val errorMessage: LiveData<SingleEvent<Any>> get() = _errorMessage

    fun doLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginLiveData.value = Resource.Loading()
            mainUseCase.postLogin(
                loginRequest = loginRequest
            ).collect {
                _loginLiveData.value = it
            }
        }
    }

    fun getToken(auth: String) {
        viewModelScope.launch {
            _token.value = Resource.Loading()
            mainUseCase.getToken(auth).collect {
                _token.value = it
            }
        }
    }

    fun storeDistribution(dist: ListDistributionItem) {
        val distribution = Distribution(dist.distributionSID, dist.distributionName)
        viewModelScope.launch {
            distUseCase.insertDistribution(distribution)
        }
        for (regionItem in dist.listRegion) {
            val region = Region(
                regionItem.regionSID,
                regionItem.regionName,
                dist.distributionSID,
                false,
                "",
                "",
                "",
                0,
                0
            )
            viewModelScope.launch {
                distUseCase.insertRegion(region)
            }
        }
    }
}