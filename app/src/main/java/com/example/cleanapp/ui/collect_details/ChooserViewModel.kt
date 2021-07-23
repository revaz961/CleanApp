package com.example.cleanapp.ui.collect_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Order

class ChooserViewModel : ViewModel() {

    private var _fragmentTitleLiveData = MutableLiveData<String>()
    val fragmentTitleLiveData: LiveData<String> = _fragmentTitleLiveData

    private var _navigateLiveData = MutableLiveData<Pair<Int, Order>>()
    val navigateLiveData: LiveData<Pair<Int, Order>> = _navigateLiveData

    fun setFragmentTitle(str: String) {
        _fragmentTitleLiveData.postValue(str)
    }

    fun navigate(id: Int, order: Order) {
        _navigateLiveData.postValue(Pair(id, order))
    }

}