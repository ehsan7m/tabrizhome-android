package com.tabrizhome.app.data.remote

import com.tabrizhome.app.data.remote.dto.PropertyDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TabrizHomeApi {
    @GET("properties")
    suspend fun getProperties(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("search") search: String? = null,
        @Query("_embed") embed: Boolean = true
    ): List<PropertyDto>

    @GET("properties/{id}")
    suspend fun getProperty(
        @Path("id") id: Long,
        @Query("_embed") embed: Boolean = true
    ): PropertyDto
}
