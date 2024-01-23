package br.com.mha.checklistauto.commands

import br.com.mha.checklistauto.sensors.VoiceSensor

class ErrorMessageCommand(
    private val voiceSensor: VoiceSensor
): Command {

    override fun evaluate(command: String) {
        voiceSensor.speech("Sorry, I can not understand you")
    }
}