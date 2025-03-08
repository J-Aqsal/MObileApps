package com.example.project4.data.model

data class SampahResponse(
    val message: String,
    val error: Int,
    val data: List<SampahItem>
)



data class SampahItem(
    val id: Int,
    val kode_provinsi: Int,
    val nama_provinsi: String,
    val kode_kabupaten_kota: Int,
    val nama_kabupaten_kota: String,
    val jumlah_sampah: Double,
    val satuan: String,
    val tahun: Int
)