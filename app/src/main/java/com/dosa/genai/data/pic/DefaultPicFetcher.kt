package com.dosa.genai.data.pic

import android.graphics.Bitmap
import javax.inject.Inject

class DefaultPicFetcher @Inject constructor(
    private val picUrlProvider: RandomPicUrlProvider,
    private val bitmapDownloader: BitmapDownloader
) : RandomPicFetcher {

    override suspend fun getRandomPic(): Bitmap {
        return bitmapDownloader.get(picUrlProvider.getRandomPicUrl())
    }
}