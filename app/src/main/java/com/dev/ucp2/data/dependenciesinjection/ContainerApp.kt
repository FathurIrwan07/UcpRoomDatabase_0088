package com.dev.ucp2.data.dependenciesinjection

import android.content.Context
import com.dev.ucp2.data.database.BrgDatabase
import com.dev.ucp2.data.repository.LocalRepositoryBrg
import com.dev.ucp2.data.repository.LocalRepositorySpl
import com.dev.ucp2.data.repository.RepositoryBrg
import com.dev.ucp2.data.repository.RepositorySpl

interface InterfaceContainerApp{
    val repositoryBrg: RepositoryBrg
    val repositorySpl: RepositorySpl
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    override val repositoryBrg: RepositoryBrg by lazy {
        LocalRepositoryBrg(BrgDatabase.getDatabase(context).barangDao())
    }
    override val repositorySpl: RepositorySpl by lazy {
        LocalRepositorySpl(BrgDatabase.getDatabase(context).suplierDao())
    }
}