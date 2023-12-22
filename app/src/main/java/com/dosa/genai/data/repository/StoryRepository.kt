package com.dosa.genai.data.repository

import android.graphics.Bitmap
import com.dosa.genai.data.model.Story
import com.dosa.genai.data.pic.RandomPicFetcher
import com.dosa.genai.data.text.StoryCreator
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val storyCreator: StoryCreator,
    private val picFetcher: RandomPicFetcher
) {
    private var story: Story? = null

    suspend fun genNewStory(): Story {
        val pic = getRandomPic()
        val originalStory = genStory(pic)
        val critique = critique(originalStory)
        val revisedStory = revise(originalStory, critique)
        story = Story(pic, originalStory, critique, revisedStory)
        return story!!
    }

    private suspend fun getRandomPic(): Bitmap = picFetcher.getRandomPic()

    private suspend fun genStory(pic: Bitmap): String = storyCreator.genStory(pic)

    private suspend fun critique(story: String): String = storyCreator.critique(story)

    private suspend fun revise(story: String, critique: String): String =
        storyCreator.revise(story, critique)

    fun getCreatedStory(): Story? = story
}