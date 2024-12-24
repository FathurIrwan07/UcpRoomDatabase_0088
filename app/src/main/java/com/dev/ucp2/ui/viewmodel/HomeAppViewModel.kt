package com.dev.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.ucp2.data.entity.Barang
import com.dev.ucp2.data.entity.Suplier
import com.dev.ucp2.data.repository.RepositoryBrg
import com.dev.ucp2.data.repository.RepositorySpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeAppViewModel(
    private val repositoryBrg: RepositoryBrg,
    private val repositorySpl: RepositorySpl
): ViewModel(){

    val homeBrgUiState: StateFlow<HomeBrgUiState> = repositoryBrg.getAllBarang()
        .filterNotNull()
        .map{
            HomeBrgUiState(
                barangList = it.toList(),
                isLoading = false,
            )
        }
        .catch {throwable ->
            emit(
                HomeBrgUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeBrgUiState(
                isLoading = true
            )
        )
    val homeSplUiState: StateFlow<HomeSplUiState> = repositorySpl.getAllSuplier()
        .filterNotNull()
        .map{
            HomeSplUiState(
                listSuplier = it.toList(),
                isLoading = false,
            )
        }
        .catch { throwable ->
            emit(
                HomeSplUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeSplUiState(
                isLoading = true
            )
        )
}

data class HomeBrgUiState(
    val barangList: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError : Boolean = false,
    val errorMessage: String = ""
)

data class HomeSplUiState(
    val listSuplier: List<Suplier> = listOf(),
    val isLoading: Boolean = false,
    val isError : Boolean = false,
    val errorMessage: String = ""
)