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
                topK = 20
                topP = 0.75f
                maxOutputTokens = 300
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
    }

    suspend fun gen(prompt: String = "Write a short story about a magic backpack."): String {
        val sb = StringBuilder()
        try {
            textModel.generateContentStream(prompt).collect { chunk ->
                Timber.d(chunk.text)
                sb.append(chunk.text)
            }
        } catch (e: ResponseStoppedException) {
            sb.append(" << MAX_TOKENS >>")
        }
        return sb.toString()
    }

    suspend fun gen(
        image: Bitmap,
        text: String = "Write a short story inspired by this picture"
    ): String {
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
            sb.append(" << MAX_TOKENS >>")
        }
        return sb.toString()
    }

    suspend fun gen(
        image1: Bitmap,
        image2: Bitmap,
        text: String = "What's different between these pictures?"
    ): String {
        val inputContent = content {
            image(image1)
            image(image2)
            text(text)
        }

        val sb = StringBuilder()
        try {
            textImageModel.generateContentStream(inputContent).collect { chunk ->
                Timber.d(chunk.text)
                sb.append(chunk.text)
            }
        } catch (e: ResponseStoppedException) {
            sb.append(" << MAX_TOKENS >>")
        }
        return sb.toString()
    }

    suspend fun chat(prompt: String = "How many paws are in my house?"): String {
        val chat = textModel.startChat(
            history = listOf(
                content(role = "user") { text("Hello, I have 2 dogs in my house.") },
                content(role = "model") { text("Great to meet you. What would you like to know?") }
            )
        )
        val inputContent = content(role = "user") { text(prompt) }

        val sb = StringBuilder()
        try {
            chat.sendMessageStream(inputContent).collect { chunk ->
                Timber.d(chunk.text)
                sb.append(chunk.text)
            }
        } catch (e: ResponseStoppedException) {
            sb.append(" << MAX_TOKENS >>")
        }
        return sb.toString()
    }
}