package br.com.mha.checklistauto.domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

class CheckListItem(
    var description: String
): RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var isDone: Boolean = false

    constructor(): this("")
}
