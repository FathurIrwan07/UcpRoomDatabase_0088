package com.dev.ucp2

import android.app.Application
import com.dev.ucp2.data.dependenciesinjection.ContainerApp


class BrgApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        //membuat instance ContainerApp
        containerApp = ContainerApp(this)
        //instance adlah object yg di buat dari class
    }
}