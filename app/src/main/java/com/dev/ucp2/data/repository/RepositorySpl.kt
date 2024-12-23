package com.dev.ucp2.data.repository


import com.dev.ucp2.data.entity.Suplier
import kotlinx.coroutines.flow.Flow

interface RepositorySpl {
    suspend fun insertSuplier(suplier: Suplier)
    fun getAllSuplier(): Flow<List<Suplier>>
    fun getSuplier(id: String): Flow<Suplier>
}

