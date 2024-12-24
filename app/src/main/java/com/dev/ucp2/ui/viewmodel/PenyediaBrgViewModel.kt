package com.dev.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dev.ucp2.BrgApp
import com.dev.ucp2.ui.viewmodel.barang.BarangViewModel
import com.dev.ucp2.ui.viewmodel.barang.DaftarBrgViewModel
import com.dev.ucp2.ui.viewmodel.barang.DetailBrgViewModel
import com.dev.ucp2.ui.viewmodel.barang.UpdateBrgViewModel
import com.dev.ucp2.ui.viewmodel.suplier.HomeSplViewModel
import com.dev.ucp2.ui.viewmodel.suplier.SuplierViewModel

object PenyediaBrgViewModel{

    val Factory = viewModelFactory {
        initializer {
            HomeAppViewModel(
                BrgApp().containerApp.repositoryBrg,
                BrgApp().containerApp.repositorySpl
            )
        }
        initializer {
            DaftarBrgViewModel(
                BrgApp().containerApp.repositoryBrg
            )
        }
        initializer {
            HomeSplViewModel(
                BrgApp().containerApp.repositorySpl
            )
        }
        initializer {
            BarangViewModel(
                BrgApp().containerApp.repositoryBrg,
            )
        }
        initializer {
            DetailBrgViewModel(
                createSavedStateHandle(),
                BrgApp().containerApp.repositoryBrg,
            )
        }
        initializer {
            UpdateBrgViewModel(
                createSavedStateHandle(),
                BrgApp().containerApp.repositoryBrg,
            )
        }
        initializer {
            SuplierViewModel(
                BrgApp().containerApp.repositorySpl
            )
        }
    }
}

fun CreationExtras.BrgApp() : BrgApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BrgApp)