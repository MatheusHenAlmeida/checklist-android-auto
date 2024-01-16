package br.com.mha.checklistauto.ui.checklists

import androidx.lifecycle.ViewModel
import br.com.mha.checklistauto.data.CheckListRepository

class CheckListsViewModel(
    private val checkListRepository: CheckListRepository
): ViewModel() {
    fun getAllCheckLists() = checkListRepository.getAllCheckLists()

    fun addNewList(listName: String) {
        checkListRepository.addNewList(listName)
    }
}