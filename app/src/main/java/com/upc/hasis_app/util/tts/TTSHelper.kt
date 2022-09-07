package com.upc.hasis_app.util.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.MutableLiveData
import com.upc.hasis_app.presentation.view_model.ResultStatus


class TTSHelper(context : Context, onInitListener: TextToSpeech.OnInitListener) {

    var tts : TextToSpeech? = null
    private var speechListener : UtteranceProgressListener? = null
    val currentState: MutableLiveData<ResultStatus> by lazy {
        MutableLiveData<ResultStatus>()
    }


    init {
        tts = TextToSpeech(context, onInitListener)

        speechListener = object : UtteranceProgressListener() {
            override fun onStart(p0: String?) {
                setState(ResultStatus.Speaking)
            }

            override fun onDone(p0: String?) {
                setState(ResultStatus.SpeakComplete)
            }

            override fun onError(p0: String?) {

            }

        }

        tts!!.setOnUtteranceProgressListener(speechListener)
    }

    private fun setState(value: ResultStatus) {
        currentState.postValue(value)
    }


    fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    fun silence() {
        tts!!.stop()
    }

    fun isComplete(): Boolean {
        return when(currentState.value) {
            is ResultStatus.SpeakComplete -> {
                true
            }
            else -> {
                false
            }
        }
    }


}