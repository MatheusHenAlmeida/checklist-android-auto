package br.com.mha.checklistauto.commands

class CreateListCommand(
    private val createListAction: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(PREFIX)) {
            val listName = command.replace("$PREFIX ", "")
            createListAction.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private companion object {
        const val PREFIX = "create list"
    }
}