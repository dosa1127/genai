package com.dosa.genai.data.pic

import android.graphics.Bitmap
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.FutureTarget
import javax.inject.Inject
import kotlin.random.Random

class PicsumPicFetcher @Inject constructor(
    private val glide: RequestManager
) : RandomPicFetcher {

    override fun getRandomPic(): Bitmap {
        val picUrl = "${PIC_SUM_URL}${IMAGE_SIZE}?${PARAM_RAND}=${Random.nextInt()}"
        return getBitmap(picUrl).get()
    }

    private fun getBitmap(imageUrl: String): FutureTarget<Bitmap> {
        return glide
            .asBitmap()
            .load(imageUrl)
            .submit()
    }

    companion object {
        private const val PIC_SUM_URL = "https://picsum.photos/"
        private const val IMAGE_SIZE = "360"
        private const val PARAM_RAND = "random="
    }
}