package br.com.mha.checklistauto.ui.checklists

import androidx.lifecycle.ViewModel
import br.com.mha.checklistauto.data.CheckListRepository
import br.com.mha.checklistauto.domain.CheckList

class CheckListsViewModel(
    private val checkListRepository: CheckListRepository
): ViewModel() {
    fun getAllCheckLists() = checkListRepository.getAllCheckLists()

    fun getListByName(listName: String): CheckList? {
        return checkListRepository.getCheckListByName(listName)
    }

    fun addNewList(listName: String) {
        checkListRepository.addNewList(listName)
    }

    fun deleteList(listName: String) {
        checkListRepository.deleteCheckListByName(listName)
    }
}