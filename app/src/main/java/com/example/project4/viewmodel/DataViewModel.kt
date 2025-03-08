package com.example.project4.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.project4.data.AppDatabase
import com.example.project4.data.DataEntity
import com.example.project4.data.model.SampahItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.example.project4.data.repository.SampahRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    application: Application,
    private val repository: SampahRepository
) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()

    private val _rowCount = MutableLiveData<Int>(0)
    val rowCount: LiveData<Int> = _rowCount

    fun fetchRowCount() {
        viewModelScope.launch {
            _rowCount.value = dao.getCount()
        }
    }
    val dataList: LiveData<List<DataEntity>> = dao.getAll()

    private val _apiDataList = MutableLiveData<List<SampahItem>>() // Data dari API
    val apiDataList: LiveData<List<SampahItem>> = _apiDataList

    fun insertData(
        kodeProvinsi: String,
        namaProvinsi: String,
        kodeKabupatenKota: String,
        namaKabupatenKota: String,
        total: String,
        satuan: String,
        tahun: String
    ) {
        viewModelScope.launch {
            val totalValue = total.toDoubleOrNull() ?: 0.0
            val tahunValue = tahun.toIntOrNull() ?: 0
            dao.insert(
                DataEntity(
                    kodeProvinsi = kodeProvinsi,
                    namaProvinsi = namaProvinsi,
                    kodeKabupatenKota = kodeKabupatenKota,
                    namaKabupatenKota = namaKabupatenKota,
                    total = totalValue,
                    satuan = satuan,
                    tahun = tahunValue
                )
            )
            withContext(Dispatchers.IO) {
                _rowCount.postValue(dao.getCount())
            }
        }
    }

    fun updateData(data: DataEntity) {
        viewModelScope.launch {
            dao.update(data)
        }
    }


    fun deleteData(data: DataEntity) {
        viewModelScope.launch {
            dao.delete(data)
            withContext(Dispatchers.IO) {
                _rowCount.postValue(dao.getCount())
            }
        }
    }

    suspend fun getDataById(id: Int): DataEntity? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }
    fun fetchApiData() {
        viewModelScope.launch {
            try {
                val response = repository.getSampah()

                Log.d("API_RESPONSE", response.body().toString())
                if (response.isSuccessful) {
                    _apiDataList.value = response.body()?.data ?: emptyList()
                } else {
                    Log.e("DataViewModel", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("DataViewModel", "Exception: ${e.message}")
            }
        }
    }
}
