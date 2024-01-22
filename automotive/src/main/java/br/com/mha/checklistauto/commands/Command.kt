package br.com.mha.checklistauto.commands

interface Command {
    fun evaluate(command: String)
}