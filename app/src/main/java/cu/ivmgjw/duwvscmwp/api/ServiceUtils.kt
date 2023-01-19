package cu.ivmgjw.duwvscmwp.api

import cu.ivmgjw.duwvscmwp.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceUtils {

    //crypto currency
    fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL_CRYPTO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getGist(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL_GIST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}