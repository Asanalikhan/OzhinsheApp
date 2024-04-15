package com.example.ozhinshe.data

import com.example.ozhinshe.modiedata.AuthRequest
import com.example.ozhinshe.modiedata.AuthorizationRespoce
import com.example.ozhinshe.modiedata.CategoryAges
import com.example.ozhinshe.modiedata.DescMovies
import com.example.ozhinshe.modiedata.GenresResponce
import com.example.ozhinshe.modiedata.MovieResponce
import com.example.ozhinshe.modiedata.Movy
import com.example.ozhinshe.modiedata.RegistrationRequest
import com.example.ozhinshe.modiedata.RegistrationResponce
import com.example.ozhinshe.modiedata.Screenshot
import com.example.ozhinshe.modiedata.SeasonResponse
import com.example.ozhinshe.modiedata.UqsasGenre
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @POST("/auth/V1/signin")
    suspend fun auth(@Body authRequest: AuthRequest):Response<AuthorizationRespoce>
    @POST("/auth/V1/signup")
    suspend fun regr(@Body registrationRequest: RegistrationRequest):Response<RegistrationResponce>
    @GET("/core/V1/movies/main")
    suspend fun getMovies(
        @Header("accept") acceptHeader: String = "application/json",
        @Header("Authorization") token: String,
    ): MovieResponce
    @GET("core/V1/genres/page")
    suspend fun getGenres(
        @Header("accept") acceptHeader: String = "application/json",
        @Header("Authorization") token: String,
    ): GenresResponce
    @GET("/core/V1/movies/page")
    suspend fun descMovies(
        @Query("direction") direction: String = "ASC",
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sortField") sortField: String = "createdDate",
        @Header("accept") acceptHeader: String = "application/json",
        @Header("Authorization") token: String,
    ): DescMovies
    @GET("/core/V1/movies/page")
    suspend fun genreMovies(
        @Query("direction") direction: String,
        @Query("genreId") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("sortField") sortField: String = "createdDate",
        @Header("accept") acceptHeader: String = "application/json",
        @Header("Authorization") token: String,
    ): DescMovies
    @GET("/core/V1/category-ages")
    suspend fun getCategoryAges(
        @Header("accept") acceptHeader: String = "application/json",
        @Header("Authorization") token: String,
    ): CategoryAges
    @GET("/core/V1/movies/{id}")
    suspend fun getMovie(
        @Path("id") id: Int?,
        @Header("Authorization") token: String,
    ): Movy
    @GET("/core/V1/movies/page")
    suspend fun uqsasMovies(
        @Query("direction") direction: String,
        @Query("genreId") genreId: Int?,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("sortField") sortField: String = "createdDate",
        @Header("accept") acceptHeader: String = "application/json",
        @Header("Authorization") token: String,
    ): UqsasGenre
    @GET("core/V1/screenshots/{movieId}")
    suspend fun getScreenshots(
        @Path("movieId") id: Int?,
        @Header("Authorization") token: String,
    ):List<Screenshot>
    @GET("core/v1/seasons/{movieId}")
    suspend fun getListSeasons(
        @Path("movieId") id: Int?,
        @Header("Authorization") token: String,
    ): SeasonResponse
}