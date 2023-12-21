package com.dosa.genai.ui.notifications

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.dosa.genai.ai.GeminiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel
@Inject constructor(private val glide: RequestManager) : ViewModel() {

    private val geminiManager: GeminiManager = GeminiManager()

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun generateStory() {
        val imageUrl = "https://picsum.photos/360?random=" + System.currentTimeMillis()

        viewModelScope.launch(Dispatchers.IO) {
            var bitmap: Bitmap? = null
            try {
                bitmap = getBitmap(imageUrl)
            } catch (e: Exception) {
                Timber.e(e)
                _text.postValue(e.message)
            }
            bitmap?.let {
                _image.postValue(bitmap!!)
                val story = geminiManager.gen(bitmap)
                _text.postValue(story)
            }
        }
    }

    private fun getBitmap(imageUrl: String): Bitmap {
        return glide
            .asBitmap()
            .load(imageUrl)
            .submit()
            .get()
    }
}