package com.upc.hasis_app.util.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.upc.hasis_app.presentation.view_model.ResultStatus
import java.util.*
import kotlin.collections.ArrayList

class STTHelper(context: Context?) {

    var speechRecognizer : SpeechRecognizer? = null
    private var speechRecognizerIntent : Intent

    init {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("es", "ES"))
    }


    fun setSpeechRecognizerListeners(onBeginning: () -> Unit, onResult: (ArrayList<String>) -> Unit) {

        speechRecognizer!!.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(p0: Bundle?) {

                }

                override fun onBeginningOfSpeech() {
                    onBeginning()
                }

                override fun onRmsChanged(p0: Float) {

                }

                override fun onBufferReceived(p0: ByteArray?) {

                }

                override fun onEndOfSpeech() {

                }

                override fun onError(p0: Int) {

                }

                override fun onResults(bundle: Bundle?) {
                    val data = bundle!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (data != null) { onResult(data) }
                }

                override fun onPartialResults(p0: Bundle?) {

                }

                override fun onEvent(p0: Int, p1: Bundle?) {

                }

            }
        )

    }

    fun listen() {
        speechRecognizer!!.startListening(speechRecognizerIntent)
    }


    fun stopListen() {
        speechRecognizer!!.stopListening()
    }

}