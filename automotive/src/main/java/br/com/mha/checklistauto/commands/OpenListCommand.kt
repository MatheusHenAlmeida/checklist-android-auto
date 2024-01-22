package br.com.mha.checklistauto.commands

class OpenListCommand(
    private val goToItemsScreen: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(PREFIX)) {
            val listName = command.replace("$PREFIX ", "")
            goToItemsScreen.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private companion object {
        const val PREFIX = "open list"
    }
}