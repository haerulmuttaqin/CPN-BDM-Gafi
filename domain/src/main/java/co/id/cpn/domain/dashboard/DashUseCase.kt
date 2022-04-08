package co.id.cpn.domain.dashboard

import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Resource
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface DashUseCase {
     fun dashboardProductivity(rctlSID: String): Flow<Resource<DataBody<JsonArray>>>
     fun dashboardReasonOrderLost(rctlSID: String): Flow<Resource<DataBody<JsonArray>>>
     fun dashboardRegisterOutlet(rctlSID: String): Flow<Resource<DataBody<JsonObject>>>
     fun dashboardSoVsDo(rctlSID: String): Flow<Resource<DataBody<JsonArray>>>
     fun dashboardSoVsDoCust(rctlSID: String, customerSID: String): Flow<Resource<DataBody<JsonArray>>>
     fun dashboardTagging(rctlSID: String): Flow<Resource<DataBody<JsonObject>>>
     fun dashboardTotalFreezer(rctlSID: String): Flow<Resource<DataBody<JsonArray>>>
     fun dashboardVisitPerformance(rctlSID: String): Flow<Resource<DataBody<JsonObject>>>
}