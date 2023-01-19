package cu.ivmgjw.duwvscmwp.api

import cu.ivmgjw.duwvscmwp.Const
import cu.ivmgjw.duwvscmwp.gist_model.GistModel
import cu.ivmgjw.duwvscmwp.models.CurrenciesModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET(Const.END_POINT)
    suspend fun currenciesData(): Response<CurrenciesModel>

    @GET(Const.END_POINT_GIST)
    suspend fun currenciesGist(): Response<GistModel>
}