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

class PicsumPicFetcherTest {
    @Mock
    private lateinit var bitmapDownloader: BitmapDownloader

    // Class under test
    private lateinit var picsumPicFetcher: PicsumPicFetcher

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        picsumPicFetcher = PicsumPicFetcher(bitmapDownloader)
    }

    @Test
    fun `getRandomPic should return a valid Bitmap`() = runTest {
        val expectedUrl = "https://picsum.photos/360?random=1"
        val expectedBitmap: Bitmap = mock(Bitmap::class.java)

        mockStatic(Uri::class.java)
        val mockUri: Uri = mock(Uri::class.java)
        val mockBuilder: Uri.Builder = mock(Uri.Builder::class.java)
        `when`(Uri.parse(anyString())).thenReturn(mockUri)
        `when`(mockUri.buildUpon()).thenReturn(mockBuilder)
        `when`(mockBuilder.appendPath(anyString())).thenReturn(mockBuilder)
        `when`(mockBuilder.appendQueryParameter(anyString(),anyString())).thenReturn(mockBuilder)
        `when`(mockBuilder.build()).thenReturn(mockUri)
        `when`(mockUri.toString()).thenReturn(expectedUrl)

        `when`(bitmapDownloader.get(anyString())).thenReturn(expectedBitmap)

        val actualBitmap: Bitmap = picsumPicFetcher.getRandomPic()
        assert(actualBitmap == expectedBitmap)
    }
}