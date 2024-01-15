package br.com.mha.checklistauto.data

import br.com.mha.checklistauto.domain.CheckList
import br.com.mha.checklistauto.domain.CheckListItem

class CheckListRepository {

    fun getAllCheckLists(): List<CheckList> {
        return emptyList()
    }

    fun getAllItemsFromCheckList(id: Int): List<CheckListItem> {
        return emptyList()
    }
}