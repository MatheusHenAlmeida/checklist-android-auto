package br.com.mha.checklistauto.domain

data class CheckList(
    val id: Int,
    val name: String,
    val items: List<CheckListItem>
)
