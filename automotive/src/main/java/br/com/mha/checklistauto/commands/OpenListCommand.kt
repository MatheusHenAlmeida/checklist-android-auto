package br.com.mha.checklistauto.commands

class OpenListCommand(
    private val goToItemsScreen: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith("open list")) {
            val listName = command.replace("open list ", "")
            goToItemsScreen.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }
}