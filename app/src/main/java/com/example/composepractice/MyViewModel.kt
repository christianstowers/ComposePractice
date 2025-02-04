package com.example.composepractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _events = MutableSharedFlow<String>(replay = 0)
    val events = _events.asSharedFlow()

    fun sendEvent(message: String) {
        viewModelScope.launch {
            _events.emit(message)
        }
    }
}