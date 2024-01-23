package br.com.mha.checklistauto.commands

class DeleteItemCommand(
    private val deleteItemAction: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(PREFIX)) {
            val listName = command.replace("$PREFIX ", "")
            deleteItemAction.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private companion object {
        const val PREFIX = "delete item"
    }
}