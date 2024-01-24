package br.com.mha.checklistauto.commands

class MarkItemCommand(
    private val changeItemStatusAction: (String) -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(CHECK_PREFIX) ||
            command.lowercase().startsWith(CHANGE_PREFIX)
            ) {
            val listName = command
                .replace("$CHECK_PREFIX ", "")
                .replace("$CHANGE_PREFIX ", "")
            changeItemStatusAction.invoke(listName)
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private companion object {
        const val CHECK_PREFIX = "check item"
        const val CHANGE_PREFIX = "change item"
    }
}