package com.dosa.genai.data.text

import android.graphics.Bitmap
import javax.inject.Inject

class GeminiStoryCreator @Inject constructor(
    private val geminiManager: GeminiManager
) : StoryCreator {

    override suspend fun genStory(pic: Bitmap): String {
        return geminiManager.gen(pic, PROMPT_PIC_TO_STORY)
    }

    override suspend fun critique(story: String): String {
        return geminiManager.gen(PROMPT_CRITIQUE.format(story))
    }

    override suspend fun revise(story: String, critiques: String): String {
        return geminiManager.gen(PROMPT_REVISE.format(story, critiques))
    }

    companion object {
        private const val PROMPT_PIC_TO_STORY = "Write a short story inspired by this picture"
        private const val PROMPT_CRITIQUE = "Critique this short story from angles of 4 people with background. Return a very brief info of each person, as well as their critiques.\n\nStory:%s"
        private const val PROMPT_REVISE = "Revise this story according to the critiques.\n\nStory:%s\n\nCritiques:%s"
    }
}