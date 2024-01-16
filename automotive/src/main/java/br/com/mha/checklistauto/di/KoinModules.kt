package br.com.mha.checklistauto.di

import br.com.mha.checklistauto.data.CheckListRepository
import br.com.mha.checklistauto.ui.MainViewModel
import br.com.mha.checklistauto.ui.checklists.CheckListsViewModel
import br.com.mha.checklistauto.ui.items.CheckListItemsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    val repositories = module {
        single { CheckListRepository() }
    }

    val viewModels = module {
        viewModel { MainViewModel() }
        viewModel { CheckListsViewModel(get()) }
        viewModel { CheckListItemsViewModel(get()) }
    }
}