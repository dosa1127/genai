package com.dosa.genai.data.pic

import android.graphics.Bitmap

interface RandomPicFetcher {

    suspend fun getRandomPic(): Bitmap
}