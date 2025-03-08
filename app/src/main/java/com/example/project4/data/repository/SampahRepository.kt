package com.example.project4.data.repository

import com.example.project4.data.remote.SampahApiService
import javax.inject.Inject

class SampahRepository @Inject constructor(
    private val apiService: SampahApiService
) {
    suspend fun getSampah() = apiService.getSampah()
}
