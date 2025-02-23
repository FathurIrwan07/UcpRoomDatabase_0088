package com.dev.ucp2.ui.viewmodel.suplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.ucp2.data.entity.Suplier
import com.dev.ucp2.data.repository.RepositorySpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeSplViewModel(
    private val repositorySpl: RepositorySpl
) : ViewModel() {
    val homesplUiState: StateFlow<HomeSplUiState> = repositorySpl.getAllSuplier()
        .filterNotNull()
        .map {
            HomeSplUiState(
                listSpl = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeSplUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeSplUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeSplUiState(
                isLoading = true,
            )
        )
}

data class HomeSplUiState(
    val listSpl: List<Suplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)