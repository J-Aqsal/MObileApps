package com.example.project4.data.remote

import com.example.project4.data.model.SampahResponse
import retrofit2.http.GET
import retrofit2.Response

interface SampahApiService {
    @GET("api-backend/bigdata/disperkim/od_16984_jumlah_sampah_yang_ditangani_berdasarkan_kabupatenkota?limit=300")
    suspend fun getSampah(): Response<SampahResponse>
}
