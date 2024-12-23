package com.dev.ucp2.data.repository


import com.dev.ucp2.data.dao.SuplierDao
import com.dev.ucp2.data.entity.Suplier
import kotlinx.coroutines.flow.Flow

class LocalRepositorySpl(
    private val suplierDao: SuplierDao
)