package com.example.ozhinshe.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MainApi {
    @POST("/auth/V1/signin")
    suspend fun auth(@Body authRequest: AuthRequest):Response<AuthorizationRespoce>

    @POST("/auth/V1/signup")
    suspend fun regr(@Body registrationRequest: RegistrationRequest):Response<RegistrationResponce>

    @GET("/core/V1/movies/main")
    suspend fun getMovies(
        @Header("accept") acceptHeader: String = "application/json",
        @Header("Authorization") token: String
    ): Movy

}