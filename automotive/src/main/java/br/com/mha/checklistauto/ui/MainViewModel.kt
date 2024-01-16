package br.com.mha.checklistauto.ui

import androidx.lifecycle.ViewModel
import br.com.mha.checklistauto.data.CheckListRepository

class MainViewModel(
    private val checkListRepository: CheckListRepository
): ViewModel() {

    fun getAllCheckLists() = checkListRepository.getAllCheckLists()

    fun getItemsFromList(listId: Int) = checkListRepository.getAllItemsFromCheckList(listId)

    fun addNewList(listName: String) {
        checkListRepository.addNewList(listName)
    }
}