package br.com.mha.checklistauto

import android.app.Application
import br.com.mha.checklistauto.di.KoinModules.viewModels
import br.com.mha.checklistauto.di.KoinModules.repositories
import br.com.mha.checklistauto.di.KoinModules.infrastructure
import br.com.mha.checklistauto.di.KoinModules.sensors
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CheckListAutoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CheckListAutoApplication)
            modules(viewModels, repositories, infrastructure, sensors)
        }
    }
}