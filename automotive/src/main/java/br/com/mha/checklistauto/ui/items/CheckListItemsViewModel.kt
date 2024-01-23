package br.com.mha.checklistauto.ui.items

import androidx.lifecycle.ViewModel
import br.com.mha.checklistauto.data.CheckListRepository

class CheckListItemsViewModel(
    private val checkListRepository: CheckListRepository
): ViewModel() {

    fun getItemsFromList(listId: String) = checkListRepository.getAllItemsFromCheckList(listId)

    fun addNewItemToList(listId: String, description: String) {
        checkListRepository.addNewItem(listId, description)
    }

    fun updateItemStatus(itemId: String, isDone: Boolean) {
        checkListRepository.updateItem(itemId, isDone)
    }

    fun toggleItemStatusByDescription(description: String) {
        checkListRepository.getItemFromCheckListByDescription(description)?.let {
            checkListRepository.updateItem(it.id, it.isDone.not())
        }
    }

    fun deleteItemByDescription(description: String) {
        checkListRepository.deleteItemByDescription(description)
    }
}