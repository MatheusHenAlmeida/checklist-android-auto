package br.com.mha.checklistauto.di

import br.com.mha.checklistauto.data.CheckListRepository
import br.com.mha.checklistauto.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    val viewModels = module {
        single { CheckListRepository() }
        viewModel { MainViewModel(get()) }
    }
}