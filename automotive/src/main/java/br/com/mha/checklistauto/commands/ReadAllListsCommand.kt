package br.com.mha.checklistauto.commands

import br.com.mha.checklistauto.domain.CheckList
import br.com.mha.checklistauto.sensors.VoiceSensor

class ReadAllListsCommand(
    private val checkLists: List<CheckList>,
    private val voiceSensor: VoiceSensor,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(PREFIX)) {
            readLists()
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private fun readLists() {
        checkLists.forEach { checkList ->
            voiceSensor.speech(checkList.name)
        }
    }


    private companion object {
        const val PREFIX = "read lists"
    }
}