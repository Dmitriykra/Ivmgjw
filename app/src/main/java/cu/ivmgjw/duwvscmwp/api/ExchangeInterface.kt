package cu.ivmgjw.duwvscmwp.api

import cu.ivmgjw.duwvscmwp.modelExchange.ModelExchange
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeInterface {

    @GET("latest/")
    suspend fun exchangeData(): Response<ModelExchange>
}