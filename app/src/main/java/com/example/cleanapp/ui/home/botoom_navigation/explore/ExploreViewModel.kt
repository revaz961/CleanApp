package com.example.cleanapp.ui.home.botoom_navigation.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.ResultHandler

class ExploreViewModel : ViewModel() {

    private val _exploreLiveData = MutableLiveData<ResultHandler<Master>>()
    val exploreLiveData: LiveData<ResultHandler<Master>> = _exploreLiveData
}