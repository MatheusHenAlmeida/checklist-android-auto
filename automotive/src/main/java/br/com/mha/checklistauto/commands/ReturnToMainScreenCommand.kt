package br.com.mha.checklistauto.commands

class ReturnToMainScreenCommand(
    private val goToMainScreenAction: () -> Unit,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(PREFIX)) {
            goToMainScreenAction.invoke()
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private companion object {
        const val PREFIX = "return to"
    }
}