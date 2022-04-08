package co.id.cpn.domain.dashboard

import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Resource
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

class DashUseCaseImpl constructor(private val repository: DashRepository): DashUseCase {

    override fun dashboardProductivity(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> =
        repository.dashboardProductivity(rctlSID)

    override fun dashboardReasonOrderLost(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> =
        repository.dashboardReasonOrderLost(rctlSID)

    override fun dashboardRegisterOutlet(rctlSID: String): Flow<Resource<DataBody<JsonObject>>> =
        repository.dashboardRegisterOutlet(rctlSID)

    override fun dashboardSoVsDo(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> =
        repository.dashboardSoVsDo(rctlSID)

    override fun dashboardSoVsDoCust(
        rctlSID: String,
        customerSID: String
    ): Flow<Resource<DataBody<JsonArray>>> =
        repository.dashboardSoVsDoCust(rctlSID, customerSID)

    override fun dashboardTagging(rctlSID: String): Flow<Resource<DataBody<JsonObject>>> =
        repository.dashboardTagging(rctlSID)

    override fun dashboardTotalFreezer(rctlSID: String): Flow<Resource<DataBody<JsonArray>>> =
        repository.dashboardTotalFreezer(rctlSID)

    override fun dashboardVisitPerformance(rctlSID: String): Flow<Resource<DataBody<JsonObject>>> =
        repository.dashboardVisitPerformance(rctlSID)

}
