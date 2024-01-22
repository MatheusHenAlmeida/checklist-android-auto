package br.com.mha.checklistauto.commands

class CreateListCommand(
    private val createListAction: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith("create list")) {
            val listName = command.replace("create list ", "")
            createListAction.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }
}