package com.example.machinori.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DashboardViewModel {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}