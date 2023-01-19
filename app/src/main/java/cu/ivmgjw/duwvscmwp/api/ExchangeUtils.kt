package cu.ivmgjw.duwvscmwp.api

import cu.ivmgjw.duwvscmwp.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExchangeUtils {

    fun getExchange(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL_EXCHANGE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}