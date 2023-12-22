package com.dosa.genai.data.text

import android.graphics.Bitmap

interface StoryCreator {

    suspend fun genStory(pic: Bitmap): String

    suspend fun critique(story: String): String

    suspend fun revise(story: String, critiques: String): String
}