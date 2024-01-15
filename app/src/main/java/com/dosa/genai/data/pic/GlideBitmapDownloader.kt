package com.dosa.genai.data.pic

import android.graphics.Bitmap
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class GlideBitmapDownloader @Inject constructor(
    private val glide: RequestManager
) : BitmapDownloader {

    override suspend fun get(imageUrl: String): Bitmap {
        return glide
            .asBitmap()
            .load(imageUrl)
            .submit()
            .get()
    }
}