package com.dev.ucp2.ui.viewmodel.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.ucp2.data.entity.Barang
import com.dev.ucp2.data.repository.RepositoryBrg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DaftarBrgViewModel(
    private val repositoryBrg: RepositoryBrg
) : ViewModel() {
    val HomeBrgUiState: StateFlow<HomeBrgUiState> = repositoryBrg.getAllBarang()
        .filterNotNull()
        .map {
            HomeBrgUiState(
                listBrg = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeBrgUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeBrgUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeBrgUiState()
        )

}

data class HomeBrgUiState (
    val listBrg: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)