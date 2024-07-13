package com.example.ozhinshe.domain.data

import com.example.ozhinshe.domain.models.AuthRequest
import com.example.ozhinshe.domain.models.AuthorizationRespoce
import com.example.ozhinshe.domain.models.CategoryAges
import com.example.ozhinshe.domain.models.DescMovies
import com.example.ozhinshe.domain.models.GenresResponce
import com.example.ozhinshe.domain.models.MovieResponce
import com.example.ozhinshe.domain.models.Movy
import com.example.ozhinshe.domain.models.RegistrationRequest
import com.example.ozhinshe.domain.models.RegistrationResponce
import com.example.ozhinshe.domain.models.Screenshot
import com.example.ozhinshe.domain.models.SearchResponce
import com.example.ozhinshe.domain.models.UqsasGenre
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
    ):  DescMovies
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
    @GET("core/V1/movies/search")
    suspend fun search(
        @Query("search") search: String,
        @Query("credentials") credentials: String,
        @Query("details") details: String,
        @Query("principal") principal: String,
        @Header("Authorization") token: String,
    ): SearchResponce
}