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

    fun getAllItemsFromCheckList(listId: String): List<CheckListItem> {
        return realm.query<CheckList>("id == $0", listId).first().find()?.items ?: emptyList()
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
        val item = realm.query<CheckListItem>("id == $0", itemId).first().find() ?: return

        realm.writeBlocking {
            findLatest(item)?.let {
                it.isDone = isDone
            }
        }
    }

    fun close() {
        realm.close()
    }
}