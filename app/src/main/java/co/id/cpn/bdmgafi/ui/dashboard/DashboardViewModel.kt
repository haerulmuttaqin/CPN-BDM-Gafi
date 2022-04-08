package co.id.cpn.bdmgafi.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.cpn.domain.dashboard.DashUseCase
import co.id.cpn.domain.distribution.DistUseCase
import co.id.cpn.domain.main.MainUseCase
import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Resource
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardViewModel constructor(
    val mainUseCase: MainUseCase,
    val dashUseCase: DashUseCase,
    val distUseCase: DistUseCase,
): ViewModel() {

    private val _distributionSelected = MutableLiveData<String>()
    val distributionSelected: LiveData<String> get() = _distributionSelected

    private val _productivity = MutableLiveData<Resource<DataBody<JsonArray>>>()
    val productivity: LiveData<Resource<DataBody<JsonArray>>> get() = _productivity

    private val _reasonOrderLost = MutableLiveData<Resource<DataBody<JsonArray>>>()
    val reasonOrderLost: LiveData<Resource<DataBody<JsonArray>>> get() = _reasonOrderLost

    private val _registerOutlet = MutableLiveData<Resource<DataBody<JsonObject>>>()
    val registerOutlet: LiveData<Resource<DataBody<JsonObject>>> get() = _registerOutlet

    private val _soVsDo = MutableLiveData<Resource<DataBody<JsonArray>>>()
    val soVsDo: LiveData<Resource<DataBody<JsonArray>>> get() = _soVsDo

    private val _tagging = MutableLiveData<Resource<DataBody<JsonObject>>>()
    val tagging: LiveData<Resource<DataBody<JsonObject>>> get() = _tagging

    private val _totalFreezer = MutableLiveData<Resource<DataBody<JsonArray>>>()
    val totalFreezer: LiveData<Resource<DataBody<JsonArray>>> get() = _totalFreezer

    private val _visitPerformance = MutableLiveData<Resource<DataBody<JsonObject>>>()
    val visitPerformance: LiveData<Resource<DataBody<JsonObject>>> get() = _visitPerformance

    fun submitSelectedDistribution(customerDistribution: String) {
        _distributionSelected.value = customerDistribution
    }

    val distributions: LiveData<List<Distribution>> = distUseCase.getDistributions()

    fun productivity() {
        viewModelScope.launch {
            _productivity.value = Resource.Loading()
            dashUseCase.dashboardProductivity(
                distributionSelected.value.toString()
            ).collect {
                _productivity.value = it
            }
        }
    }

    fun reasonOrderLost() {
        viewModelScope.launch {
            _reasonOrderLost.value = Resource.Loading()
            dashUseCase.dashboardReasonOrderLost(
                distributionSelected.value.toString()
            ).collect {
                _reasonOrderLost.value = it
            }
        }
    }

    fun registerOutlet() {
        viewModelScope.launch {
            _registerOutlet.value = Resource.Loading()
            dashUseCase.dashboardRegisterOutlet(
                distributionSelected.value.toString()
            ).collect {
                _registerOutlet.value = it
            }
        }
    }

    fun soVsDo() {
        viewModelScope.launch {
            _soVsDo.value = Resource.Loading()
            dashUseCase.dashboardSoVsDo(
                distributionSelected.value.toString()
            ).collect {
                _soVsDo.value = it
            }
        }
    }

    fun tagging() {
        viewModelScope.launch {
            _tagging.value = Resource.Loading()
            dashUseCase.dashboardTagging(
                distributionSelected.value.toString()
            ).collect {
                _tagging.value = it
            }
        }
    }

    fun totalFreezer() {
        viewModelScope.launch {
            _totalFreezer.value = Resource.Loading()
            dashUseCase.dashboardTotalFreezer(
                distributionSelected.value.toString()
            ).collect {
                _totalFreezer.value = it
            }
        }
    }

    fun visitPerformance() {
        viewModelScope.launch {
            _visitPerformance.value = Resource.Loading()
            dashUseCase.dashboardVisitPerformance(
                distributionSelected.value.toString()
            ).collect {
                _visitPerformance.value = it
            }
        }
    }

}