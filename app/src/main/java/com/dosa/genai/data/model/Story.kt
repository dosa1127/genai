package com.dosa.genai.data.model

import android.graphics.Bitmap

data class Story(
    val pic: Bitmap,
    val originalStory: String,
    val critiques: String,
    val revisedStory: String
)
