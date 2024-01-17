package br.com.mha.checklistauto.di

import android.speech.SpeechRecognizer
import br.com.mha.checklistauto.data.CheckListRepository
import br.com.mha.checklistauto.domain.CheckList
import br.com.mha.checklistauto.domain.CheckListItem
import br.com.mha.checklistauto.sensors.VoiceSensor
import br.com.mha.checklistauto.ui.MainViewModel
import br.com.mha.checklistauto.ui.checklists.CheckListsViewModel
import br.com.mha.checklistauto.ui.items.CheckListItemsViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    val infrastructure = module {
        single<Realm> {
            val configuration = RealmConfiguration.create(setOf(CheckList::class, CheckListItem::class))
            Realm.open(configuration)
        }
        factory {
            SpeechRecognizer.createSpeechRecognizer(androidContext())
        }
    }

    val sensors = module {
        factory { VoiceSensor(get()) }
    }

    val repositories = module {
        single { CheckListRepository(get()) }
    }

    val viewModels = module {
        viewModel { MainViewModel(get()) }
        viewModel { CheckListsViewModel(get()) }
        viewModel { CheckListItemsViewModel(get()) }
    }
}