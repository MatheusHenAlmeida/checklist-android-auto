package br.com.mha.checklistauto.sensors

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH
import android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL
import android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
import android.speech.RecognizerIntent.EXTRA_PARTIAL_RESULTS
import android.speech.RecognizerIntent.EXTRA_LANGUAGE
import android.speech.RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
import android.speech.SpeechRecognizer

class VoiceSensor(
    private val speechRecognizer: SpeechRecognizer
) {

    fun setCallbacks(
        onStart: () -> Unit,
        onCommandListened: (String) -> Unit
    ) {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {}

            override fun onBeginningOfSpeech() {
                onStart.toString()
            }

            override fun onRmsChanged(p0: Float) {}

            override fun onBufferReceived(p0: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(p0: Int) {}

            override fun onResults(p0: Bundle?) {
                val data = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                onCommandListened.invoke(data?.joinToString() ?: "")
            }

            override fun onPartialResults(p0: Bundle?) {}

            override fun onEvent(p0: Int, p1: Bundle?) {}

        })
    }

    fun startListening() {
        val recognzerIntent = Intent(ACTION_RECOGNIZE_SPEECH)
        recognzerIntent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_WEB_SEARCH)
        recognzerIntent.putExtra(EXTRA_PARTIAL_RESULTS, false)
        recognzerIntent.putExtra(EXTRA_LANGUAGE, "en-IN")

        speechRecognizer.startListening(recognzerIntent)
    }
}