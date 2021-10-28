package br.com.iesb.qualeonumero.model

import retrofit2.Response
import retrofit2.http.GET

interface RandomNumberApi {

    @GET("rand?min=1&max=300")
    suspend fun fetchRandomNumber() : Response<RandomNumber>

}