package com.dev.ucp2.ui.view.barang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.ucp2.data.NamaSuplier
import com.dev.ucp2.ui.customwidget.DropDownSpl
import com.dev.ucp2.ui.customwidget.TopAppBar
import com.dev.ucp2.ui.viewmodel.PenyediaBrgViewModel
import com.dev.ucp2.ui.viewmodel.barang.BarangEvent
import com.dev.ucp2.ui.viewmodel.barang.BarangViewModel
import com.dev.ucp2.ui.viewmodel.barang.BrgUiState
import com.dev.ucp2.ui.viewmodel.barang.FormBrgErrorState
import kotlinx.coroutines.launch

@Composable
fun InsertBrgView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangViewModel = viewModel(factory = PenyediaBrgViewModel.Factory)
) {
    val uiState = viewModel.BrgUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Barang",
                modifier = modifier,
            )
            InsertBodyBrg(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateUiState(updateEvent)
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyBrg(
    modifier: Modifier = Modifier,
    onValueChange: (BarangEvent) -> Unit,
    uiState: BrgUiState,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            BarangForm(
                barangEvent = uiState.barangEvent,
                onValueChange = onValueChange,
                errorState = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            )
        }


        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Simpan",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }

}

@Composable
fun BarangForm(
    barangEvent: BarangEvent = BarangEvent(),
    onValueChange: (BarangEvent) -> Unit = { },
    errorState: FormBrgErrorState = FormBrgErrorState(),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.idbarang,
            onValueChange = {
                onValueChange(barangEvent.copy(idbarang = it))
            },
            label = { Text(text = "Id Barang") },
            isError = errorState.idbarang != null,
            placeholder = { Text(text = "Masukkan Id Barang") }
        )
        Text(
            text = errorState.idbarang ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.padding(3.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.nama,
            onValueChange = {
                onValueChange(barangEvent.copy(nama = it))
            },
            label = { Text(text = "Nama Barang") },
            isError = errorState.nama != null,
            placeholder = { Text(text = "Masukkan Nama Barang") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.padding(3.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.deskripsi,
            onValueChange = {
                onValueChange(barangEvent.copy(deskripsi = it))
            },
            label = { Text(text = "Deskripsi") },
            isError = errorState.deskripsi != null,
            placeholder = { Text(text = "Masukkan Deskripsi") }
        )
        Text(
            text = errorState.deskripsi ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.padding(3.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.harga.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0 // Tangani input kosong atau tidak valid
                onValueChange(barangEvent.copy(harga = newValue))
            },
            label = { Text(text = "Harga") },
            isError = errorState.harga != null,
            placeholder = { Text(text = "Masukkan Harga") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        Text(
            text = errorState.harga ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.padding(3.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.stok.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0 // Tangani input kosong atau tidak valid
                onValueChange(barangEvent.copy(stok = newValue))
            },
            label = { Text(text = "Stok") },
            isError = errorState.stok != null,
            placeholder = { Text(text = "Masukkan Stok") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        Text(
            text = errorState.stok ?: "",
            color = Color.Red
        )


        Spacer(modifier = Modifier.padding(3.dp))
        DropDownSpl(
            selectedValue = barangEvent.nama_suplier,
            options = NamaSuplier.options(),
            label = "Nama Suplier",
            onValueChangedEvent = { selectedSupplier ->
                onValueChange(barangEvent.copy(nama_suplier = selectedSupplier))
            }
        )
        if (errorState.nama_suplier != null) {
            Text(
                text = errorState.nama_suplier,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}