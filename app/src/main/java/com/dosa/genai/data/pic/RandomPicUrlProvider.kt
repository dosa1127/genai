package com.dosa.genai.data.pic

interface RandomPicUrlProvider {

    suspend fun getRandomPicUrl(): String
}
