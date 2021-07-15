package com.example.cleanapp.ui.collect_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChooserViewModel: ViewModel() {

    private var _fragmentTitleLiveData = MutableLiveData<String>()
    val fragmentTitleLiveData: LiveData<String> = _fragmentTitleLiveData

    fun setFragmentTitle(str: String) {
        _fragmentTitleLiveData.postValue(str)
    }

}