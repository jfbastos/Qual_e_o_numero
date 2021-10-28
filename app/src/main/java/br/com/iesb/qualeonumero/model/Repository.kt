package br.com.iesb.qualeonumero.model

import br.com.iesb.qualeonumero.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository{

    private val api : RandomNumberApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RandomNumberApi::class.java)

    suspend fun fetchRandomNumber() : Response<RandomNumber> {
        return api.fetchRandomNumber()
    }

}
