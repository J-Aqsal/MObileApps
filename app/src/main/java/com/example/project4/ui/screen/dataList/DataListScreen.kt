package com.example.project4.ui.screen.dataList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.project4.viewmodel.DataViewModel

@Composable
fun DataListScreen(navController: NavHostController, viewModel: DataViewModel) {
    val dataList by viewModel.dataList.observeAsState(emptyList())
    val apiDataList by viewModel.apiDataList.observeAsState(emptyList())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchApiData()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Data Lokal
        item {
            Text(
                text = "Data Lokal",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        if (dataList.isEmpty()) {
            item {
                Text(
                    text = "Tidak ada data lokal",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            items(dataList) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Provinsi: ${item.namaProvinsi} (${item.kodeProvinsi})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Kabupaten/Kota: ${item.namaKabupatenKota} (${item.kodeKabupatenKota})",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Total: ${item.total} ${item.satuan}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tahun: ${item.tahun}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("edit/${item.id}")
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(text = "Edit")
                            }

                            Spacer(modifier = Modifier.width(5.dp))
                            Button(
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        "Data berhasil dihapus!",
                                        Toast.LENGTH_SHORT).show()
                                    viewModel.deleteData(item)
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(text = "Delete")
                            }
                        }
                    }
                }
            }
        }

        // Pemisah
        item {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Data dari API",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Data API
        if (apiDataList.isEmpty()) {
            item {
                Text(
                    text = "Tidak ada data dari API",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            items(apiDataList) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Provinsi: ${item.nama_provinsi} (${item.kode_provinsi})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Kabupaten/Kota: ${item.nama_kabupaten_kota} (${item.kode_kabupaten_kota})",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Jumlah Produksi Sampah: ${item.jumlah_sampah} ${item.satuan}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tahun: ${item.tahun}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
