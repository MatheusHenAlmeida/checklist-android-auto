package br.com.mha.checklistauto.data

import br.com.mha.checklistauto.domain.CheckList
import br.com.mha.checklistauto.domain.CheckListItem

class CheckListRepository {

    fun getAllCheckLists(): List<CheckList> {
        return listOf(list1, list2, list3)
    }

    fun getAllItemsFromCheckList(id: Int): List<CheckListItem> {
        return listOf(item1, item2, item3)
    }

    private val item1 = CheckListItem(1, "Item 1", false)
    private val item2 = CheckListItem(2, "Item 2", true)
    private val item3 = CheckListItem(3, "Item 1", false)

    private val list1 = CheckList(1, "List 1", listOf(item1, item2, item3))
    private val list2 = CheckList(2, "List 2", listOf(item1, item2, item3))
    private val list3 = CheckList(3, "List 3", listOf(item1, item2, item3))
}