package br.com.mha.checklistauto.commands

import br.com.mha.checklistauto.domain.CheckListItem
import br.com.mha.checklistauto.sensors.VoiceSensor

class ReadItemsCommand(
    private val items: List<CheckListItem>,
    private val voiceSensor: VoiceSensor,
    private val nextCommandToBeEvaluated: Command
): Command {

    override fun evaluate(command: String) {
        if (command.lowercase().startsWith(PREFIX)) {
            readItems()
        } else {
            nextCommandToBeEvaluated.evaluate(command)
        }
    }

    private fun readItems() {
        items.forEach { item ->
            voiceSensor.speech(item.description)
        }
    }

    private companion object {
        const val PREFIX = "read items"
    }
}