package com.dosa.genai.ui.critique

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
class CritiqueViewModel
@Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun chat() {
        viewModelScope.launch(Dispatchers.IO) {
            val story = storyRepository.getCreatedStory()?.critiques
            _text.postValue(story)
        }
    }
}