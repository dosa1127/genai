package com.dosa.genai.data.pic

import android.graphics.Bitmap

interface BitmapDownloader {
    suspend fun get(imageUrl: String): Bitmap
}