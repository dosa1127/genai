package com.dosa.genai

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GenAiApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}