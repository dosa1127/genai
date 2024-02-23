package com.dosa.genai.data.repository

import android.graphics.Bitmap
import com.dosa.genai.data.pic.RandomPicFetcher
import com.dosa.genai.data.text.StoryCreator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
//@RunWith(AndroidJUnit4::class)
class StoryRepositoryTest {
    @Mock
    private lateinit var storyCreator: StoryCreator

    @Mock
    private lateinit var picFetcher: RandomPicFetcher

    // Class under test
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        storyRepository = StoryRepository(storyCreator, picFetcher)
    }

    @Test
    fun genNewStory_returns_valid_story() = runTest {
        // Given
        val mockBitmap = mock<Bitmap>()
        val originalStory = "Original Story"
        val critiques = "Critiques"
        val revisedStory = "Revised Story"
        Mockito.`when`(picFetcher.getRandomPic()).thenReturn(mockBitmap)
        Mockito.`when`(storyCreator.genStory(mockBitmap)).thenReturn(originalStory)
        Mockito.`when`(storyCreator.critique(originalStory)).thenReturn(critiques)
        Mockito.`when`(storyCreator.revise(originalStory, critiques)).thenReturn(revisedStory)

        // When
        val result = storyRepository.genNewStory()

        // Then
        assertEquals(mockBitmap, result.pic)
        assertEquals(originalStory, result.originalStory)
        assertEquals(critiques, result.critiques)
        assertEquals(revisedStory, result.revisedStory)
    }

    @Test
    fun getCreatedStory_returns_generated_story() = runTest {
        // Given
        val mockBitmap = mock<Bitmap>()
        val originalStory = "Original Story"
        val critiques = "Critiques"
        val revisedStory = "Revised Story"
        Mockito.`when`(picFetcher.getRandomPic()).thenReturn(mockBitmap)
        Mockito.`when`(storyCreator.genStory(mockBitmap)).thenReturn(originalStory)
        Mockito.`when`(storyCreator.critique(originalStory)).thenReturn(critiques)
        Mockito.`when`(storyCreator.revise(originalStory, critiques)).thenReturn(revisedStory)

        // When
        val result = storyRepository.genNewStory()
        val createdStory = storyRepository.getCreatedStory()

        // Then
        assertEquals(result, createdStory)
    }

    @Test
    fun getCreatedStory_returns_null_if_genNewStory_not_called() = runTest {
        assertNull(storyRepository.getCreatedStory())
    }
}