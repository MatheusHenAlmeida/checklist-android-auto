package br.com.mha.checklistauto.domain

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

class CheckList(
    var name: String
): RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var items: RealmList<CheckListItem> = realmListOf<CheckListItem>()

    constructor(): this("")
}
