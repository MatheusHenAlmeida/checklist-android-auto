package br.com.mha.checklistauto.data

import br.com.mha.checklistauto.domain.CheckList
import br.com.mha.checklistauto.domain.CheckListItem
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class CheckListRepository(
    private val realm: Realm
) {

    fun getAllCheckLists(): List<CheckList> {
        return realm.query<CheckList>().find().toList()
    }

    fun getAllItemsFromCheckList(id: String): List<CheckListItem> {
        return realm.query<CheckList>("id == $0", id).first().find()?.items ?: emptyList()
    }

    fun addNewList(listName: String) {
        realm.writeBlocking {
            copyToRealm(CheckList(listName))
        }
    }

    fun addNewItem(listId: String, description: String) {
        val checkList = realm.query<CheckList>("id == $0", listId).first().find() ?: return

        realm.writeBlocking {
            findLatest(checkList)?.let {
                it.items.add(CheckListItem(description))
            }
        }
    }

    fun updateItem(
        itemId: String,
        isDone: Boolean
    ) {

    }
}