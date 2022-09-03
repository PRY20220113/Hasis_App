package com.upc.hasis_app.util.tts

import android.content.Context
import android.speech.tts.TextToSpeech

class TTSHelper(context : Context, onInitListener: TextToSpeech.OnInitListener) {

    var tts : TextToSpeech? = null

    init {
        tts = TextToSpeech(context, onInitListener)
    }

    fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }


}