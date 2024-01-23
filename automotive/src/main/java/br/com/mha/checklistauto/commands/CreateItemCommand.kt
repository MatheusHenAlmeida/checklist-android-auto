package br.com.mha.checklistauto.commands

class CreateItemCommand(
    private val createItemAction: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(PREFIX)) {
            val listName = command.replace("$PREFIX ", "")
            createItemAction.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private companion object {
        const val PREFIX = "create item"
    }
}