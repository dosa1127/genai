package com.dosa.genai.data.pic

import android.graphics.Bitmap
import android.net.Uri
import javax.inject.Inject
import kotlin.random.Random

class PicsumPicFetcher @Inject constructor(
    private val bitmapDownloader: BitmapDownloader
) : RandomPicFetcher {

    override suspend fun getRandomPic(): Bitmap {
        return bitmapDownloader.get(getRandomPicUrl())
    }

    private fun getRandomPicUrl(): String {
        return Uri.parse(PIC_SUM_URL)
            .buildUpon()
            .appendPath(IMAGE_SIZE)
            .appendQueryParameter(PARAM_RAND, Random.nextInt().toString())
            .build()
            .toString()
    }

    companion object {
        private const val PIC_SUM_URL = "https://picsum.photos/"
        private const val IMAGE_SIZE = "360"
        private const val PARAM_RAND = "random"
    }
}