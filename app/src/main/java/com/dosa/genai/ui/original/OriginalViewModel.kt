package com.dosa.genai.ui.original

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosa.genai.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OriginalViewModel
@Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun generateStory() {
        viewModelScope.launch(Dispatchers.IO) {
            if (storyRepository.getCreatedStory() == null) {
                storyRepository.genNewStory()
            }
            _image.postValue(storyRepository.getCreatedStory()?.pic)
            _text.postValue(storyRepository.getCreatedStory()?.originalStory)
        }
    }
}