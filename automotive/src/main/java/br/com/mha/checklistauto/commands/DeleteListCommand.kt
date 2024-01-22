package br.com.mha.checklistauto.commands

class DeleteListCommand(
    private val deleteListAction: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith("delete list")) {
            val listName = command.replace("delete list ", "")
            deleteListAction.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }
}