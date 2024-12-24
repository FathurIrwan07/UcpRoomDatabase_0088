package com.dev.ucp2.ui.view.barang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.ucp2.ui.customwidget.TopAppBar
import com.dev.ucp2.ui.viewmodel.PenyediaBrgViewModel
import com.dev.ucp2.ui.viewmodel.suplier.FormSplErrorState
import com.dev.ucp2.ui.viewmodel.suplier.SuplierEvent
import com.dev.ucp2.ui.viewmodel.suplier.SuplierUiState
import com.dev.ucp2.ui.viewmodel.suplier.SuplierViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertSplView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SuplierViewModel = viewModel(factory = PenyediaBrgViewModel.Factory)
) {
    val uiState = viewModel.SplUiState
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
        Modifier, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { it
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Suplier",
                modifier = modifier
            )
            InsertBodySpl(
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
fun InsertBodySpl(
    modifier: Modifier = Modifier,
    onValueChange: (SuplierEvent) -> Unit,
    uiState: SuplierUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormSuplier(
            suplierEvent = uiState.suplierEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormSuplier(
    suplierEvent: SuplierEvent,
    onValueChange: (SuplierEvent) -> Unit = {},
    errorState: FormSplErrorState = FormSplErrorState(),
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(top = 20.dp).padding(15.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.idsuplier,
            onValueChange = { onValueChange(suplierEvent.copy(idsuplier = it)) },
            label = { Text(text = "Id Suplier") },
            isError = errorState.idsuplier != null,
            placeholder = { Text(text = "Masukkan Id Suplier") }
        )
        Text(
            text = errorState.idsuplier ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.nama_suplier,
            onValueChange = { onValueChange(suplierEvent.copy(nama_suplier = it)) },
            label = { Text(text = "Nama Suplier") },
            isError = errorState.nama_suplier != null,
            placeholder = { Text(text = "Masukkan Nama Suplier") }
        )
        Text(
            text = errorState.nama_suplier ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.kontak,
            onValueChange = { onValueChange(suplierEvent.copy(kontak = it)) },
            label = { Text(text = "Kontak") },
            isError = errorState.kontak != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        Text(
            text = errorState.kontak ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.alamat,
            onValueChange = { onValueChange(suplierEvent.copy(alamat = it)) },
            label = { Text(text = "Alamat") },
            isError = errorState.alamat != null,
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )
    }
}