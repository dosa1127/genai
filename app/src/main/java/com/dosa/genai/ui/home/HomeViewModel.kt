package com.dosa.genai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosa.genai.ai.GeminiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val geminiManager: GeminiManager = GeminiManager()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun generateStory() {
        viewModelScope.launch(Dispatchers.IO) {
            val story = geminiManager.gen("Write a short story about a magic backpack.")
            _text.postValue(story)
        }
    }
}