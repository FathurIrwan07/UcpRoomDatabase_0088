package com.dev.ucp2.data.repository


import com.dev.ucp2.data.dao.SuplierDao
import com.dev.ucp2.data.entity.Suplier
import kotlinx.coroutines.flow.Flow

class LocalRepositorySpl(
    private val suplierDao: SuplierDao
) : RepositorySpl {
    override suspend fun insertSuplier(suplier: Suplier) {
        suplierDao.insertSuplier(suplier)
    }
    override fun getAllSuplier(): Flow<List<Suplier>> =
        suplierDao.getAllSuplier()

    override fun getSuplier(id: String): Flow<Suplier> =
        suplierDao.getSuplier(id)
}