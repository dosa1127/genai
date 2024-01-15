package com.dosa.genai.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.dosa.genai.data.pic.GlideBitmapDownloader
import com.dosa.genai.data.pic.BitmapDownloader
import com.dosa.genai.data.pic.PicsumPicFetcher
import com.dosa.genai.data.pic.RandomPicFetcher
import com.dosa.genai.data.repository.StoryRepository
import com.dosa.genai.data.text.GeminiManager
import com.dosa.genai.data.text.GeminiStoryCreator
import com.dosa.genai.data.text.StoryCreator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context): RequestManager =
        Glide.with(context)

    @Singleton
    @Provides
    fun provideBitmapDownloader(glide: RequestManager): BitmapDownloader = GlideBitmapDownloader(glide)

    @Singleton
    @Provides
    fun provideRandomPicFetcher(picDownloader: BitmapDownloader): RandomPicFetcher = PicsumPicFetcher(picDownloader)

    @Singleton
    @Provides
    fun provideGeminiManager(): GeminiManager = GeminiManager()

    @Singleton
    @Provides
    fun provideStoryCreator(geminiManager: GeminiManager): StoryCreator =
        GeminiStoryCreator(geminiManager)

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyCreator: StoryCreator,
        picFetcher: RandomPicFetcher
    ): StoryRepository =
        StoryRepository(storyCreator, picFetcher)
}