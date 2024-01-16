package br.com.mha.checklistauto.ui.items

import androidx.lifecycle.ViewModel
import br.com.mha.checklistauto.data.CheckListRepository

class CheckListItemsViewModel(
    private val checkListRepository: CheckListRepository
): ViewModel() {

    fun getItemsFromList(listId: Int) = checkListRepository.getAllItemsFromCheckList(listId)

    fun addNewItemToList(listId: Int, description: String) {
        checkListRepository.addNewItem(listId, description)
    }

    fun updateItemStatus(itemId: Int, isDone: Boolean) {
        checkListRepository.updateItem(itemId, isDone)
    }
}