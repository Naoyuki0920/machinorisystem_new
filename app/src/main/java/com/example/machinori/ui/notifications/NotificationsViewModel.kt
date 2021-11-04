package com.example.machinori.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ここに交通安全動画を埋め込み"
    }
    val text: LiveData<String> = _text
}