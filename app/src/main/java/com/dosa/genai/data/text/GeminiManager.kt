package com.dosa.genai.data.text

import android.graphics.Bitmap
import com.dosa.genai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import timber.log.Timber

class GeminiManager {
    companion object {
        private val config by lazy {
            generationConfig {
                temperature = 0.9f
                topP = 0.75f
                topK = 32
                maxOutputTokens = 1000
            }
        }

        private val safetySettings: List<SafetySetting> by lazy {
            listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE)
            )
        }

        private val textModel: GenerativeModel by lazy {
            GenerativeModel(
                modelName = "gemini-pro",
                apiKey = BuildConfig.apiKeyGemini,
                generationConfig = config,
                safetySettings = safetySettings
            )
        }

        private val textImageModel: GenerativeModel by lazy {
            GenerativeModel(
                modelName = "gemini-pro-vision",
                apiKey = BuildConfig.apiKeyGemini,
                generationConfig = config,
                safetySettings = safetySettings
            )
        }

        private const val MAX_TOKENS = " << To Be Continued... >>"
    }

    suspend fun gen(prompt: String): String {
        val sb = StringBuilder()
        try {
            textModel.generateContentStream(prompt).collect { chunk ->
                Timber.d(chunk.text)
                sb.append(chunk.text)
            }
        } catch (e: ResponseStoppedException) {
            sb.append(MAX_TOKENS)
        }
        return sb.toString()
    }

    suspend fun gen(image: Bitmap, text: String): String {
        val inputContent = content {
            image(image)
            text(text)
        }

        val sb = StringBuilder()
        try {
            textImageModel.generateContentStream(inputContent).collect { chunk ->
                Timber.d(chunk.text)
                sb.append(chunk.text)
            }
        } catch (e: ResponseStoppedException) {
            sb.append(MAX_TOKENS)
        }
        return sb.toString()
    }
}