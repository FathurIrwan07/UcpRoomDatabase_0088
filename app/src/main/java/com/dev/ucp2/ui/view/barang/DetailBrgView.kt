package com.dev.ucp2.ui.view.barang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.ucp2.ui.customwidget.TopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.ucp2.data.entity.Barang
import com.dev.ucp2.ui.viewmodel.PenyediaBrgViewModel
import com.dev.ucp2.ui.viewmodel.barang.DetailBrgUiState
import com.dev.ucp2.ui.viewmodel.barang.DetailBrgViewModel
import com.dev.ucp2.ui.viewmodel.barang.toBarangEntity

@Composable
fun DetailBrgView(
    modifier: Modifier = Modifier,
    viewModel: DetailBrgViewModel = viewModel(factory = PenyediaBrgViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                TopAppBar(
                    onBack = onBack,
                    showBackButton = true,
                    judul = "Detail Barang",
                    modifier = modifier,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailBrgUiState.value.detailBrgUiEvent.idbarang)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        val detailBrgUiState by viewModel.detailBrgUiState.collectAsState()

        BodyDetailBrg(
            modifier = Modifier.padding(innerPadding),
            detailBrgUiState = detailBrgUiState,
            onDeleteClick = {
                viewModel.deleteBrg()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailBrg(
    modifier: Modifier = Modifier,
    detailBrgUiState: DetailBrgUiState,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        detailBrgUiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailBrgUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 62.dp)
            ) {
                ItemDetailBrg(
                    barang = detailBrgUiState.detailBrgUiEvent.toBarangEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(
                        text = "Hapus Data",
                        color = MaterialTheme.colorScheme.onError
                    )
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailBrgUiState.isUiEventEmpty -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailBrg(
    modifier: Modifier = Modifier,
    barang: Barang
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailBarang(judul = "Id Barang", isi = barang.idbarang)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailBarang(judul = "Nama Barang", isi = barang.nama)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailBarang(judul = "Harga", isi = barang.harga.toString())
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailBarang(judul = "Stok", isi = barang.stok.toString())
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailBarang(judul = "Deskripsi", isi = barang.deskripsi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailBarang(judul = "Id Suplier", isi = barang.nama_suplier)
        }
    }
}

@Composable
fun ComponentDetailBarang(
    modifier: Modifier = Modifier,
    judul: String,
    isi: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isi,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Hapus")
            }
        }
    )
}