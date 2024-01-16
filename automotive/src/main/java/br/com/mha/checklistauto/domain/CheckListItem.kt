package br.com.mha.checklistauto.domain

data class CheckListItem(
    val id: Int,
    val description: String,
    val isDone: Boolean
)
