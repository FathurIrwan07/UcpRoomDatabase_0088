package com.dev.ucp2.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.ucp2.ui.viewmodel.HomeAppViewModel
import com.dev.ucp2.ui.viewmodel.PenyediaBrgViewModel

object NamaSuplier {
    @Composable
    fun options(
        supplierHomeViewModel: HomeAppViewModel = viewModel(factory = PenyediaBrgViewModel.Factory)
    ): List<String> {
        val dataNama by supplierHomeViewModel.homeSplUiState.collectAsState()
        val namaSupplier = dataNama.listSuplier.map { it.nama_suplier }
        return namaSupplier
    }
}