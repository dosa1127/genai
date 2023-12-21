package com.dosa.genai.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosa.genai.ai.GeminiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val geminiManager: GeminiManager = GeminiManager()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun chat() {
        viewModelScope.launch(Dispatchers.IO) {
            val story = geminiManager.chat()
            _text.postValue(story)
        }
    }
}