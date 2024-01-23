package br.com.mha.checklistauto.sensors

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH
import android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL
import android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
import android.speech.RecognizerIntent.EXTRA_PARTIAL_RESULTS
import android.speech.RecognizerIntent.EXTRA_LANGUAGE
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import java.util.Locale

class VoiceSensor(
    private val speechRecognizer: SpeechRecognizer,
    private val textToSpeech: TextToSpeech
) {
    var isListening = false

    fun setCallbacks(
        onStart: () -> Unit,
        onCommandListened: (String) -> Unit,
        onComplete: () -> Unit
    ) {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {}

            override fun onBeginningOfSpeech() {
                onStart.toString()
            }

            override fun onRmsChanged(p0: Float) {}

            override fun onBufferReceived(p0: ByteArray?) {}

            override fun onEndOfSpeech() {
                isListening = false
                onComplete.invoke()
            }

            override fun onError(p0: Int) {
                isListening = false
                onComplete.invoke()
            }

            override fun onResults(p0: Bundle?) {
                val data = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                onCommandListened.invoke(data?.joinToString() ?: "")
                isListening = false
                onComplete.invoke()
            }

            override fun onPartialResults(p0: Bundle?) {}

            override fun onEvent(p0: Int, p1: Bundle?) {}

        })
    }

    fun startListening() {
        val recognizerIntent = Intent(ACTION_RECOGNIZE_SPEECH)
        recognizerIntent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent.putExtra(EXTRA_PARTIAL_RESULTS, false)
        recognizerIntent.putExtra(EXTRA_LANGUAGE, "en-IN")

        isListening = true
        speechRecognizer.startListening(recognizerIntent)
    }

    fun stopListening() {
        isListening = false
        speechRecognizer.stopListening()
    }

    fun speech(message: String) {
        textToSpeech.setLanguage(Locale.US)
        textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null)
    }
}