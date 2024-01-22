package br.com.mha.checklistauto.commands

import br.com.mha.checklistauto.domain.CheckList
import br.com.mha.checklistauto.sensors.VoiceSensor

class ReadAllListsCommand(
    private val checkLists: List<CheckList>,
    private val voiceSensor: VoiceSensor,
    private val nextCommandToBeEvaluated: Command
): Command {

    private fun readLists() {
        checkLists.forEach { checkList ->
            voiceSensor.speech(checkList.name)
        }
    }

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith("read lists")) {
            readLists()
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }
}