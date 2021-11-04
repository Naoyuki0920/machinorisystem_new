package com.example.machinori.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ここにお知らせ一覧を表示"
    }
    val text: LiveData<String> = _text
}