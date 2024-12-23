package com.dev.ucp2.data.repository

import com.dev.ucp2.data.dao.BarangDao
import com.dev.ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow

class LocalRepositoryBrg(
    private val barangDao: BarangDao
)