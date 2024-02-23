package com.dosa.genai.data.pic

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DefaultPicFetcherTest {
    @Mock
    private lateinit var picUrlProvider: RandomPicUrlProvider
    @Mock
    private lateinit var bitmapDownloader: BitmapDownloader

    // Class under test
    private lateinit var picFetcher: DefaultPicFetcher

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        picFetcher = DefaultPicFetcher(picUrlProvider, bitmapDownloader)
    }

    @Test
    fun `getRandomPic should return a valid Bitmap`() = runTest {
        val expectedUrl = "https://picsum.photos/360?random=1"
        val expectedBitmap: Bitmap = mock(Bitmap::class.java)

        `when`(picUrlProvider.getRandomPicUrl()).thenReturn(expectedUrl)
        `when`(bitmapDownloader.get(anyString())).thenReturn(expectedBitmap)

        val actualBitmap: Bitmap = picFetcher.getRandomPic()
        assert(actualBitmap == expectedBitmap)
    }
}