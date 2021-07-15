package com.example.cleanapp.ui.home.search_results

import com.airbnb.epoxy.EpoxyController
import com.example.cleanapp.R
import com.example.cleanapp.databinding.VhSearchResultBinding
import com.example.cleanapp.epoxy.ViewBindingKotlinModel
import com.example.cleanapp.extensions.load
import com.example.cleanapp.models.SearchResultMaster

class SearchResultsAdapter(
    private val clickLi
) : EpoxyController() {

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var searchResults = mutableListOf<SearchResultMaster>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            //todo show loading state
            return
        }

        if (searchResults.isEmpty()) {
            //todo show empty state
            return
        }

        searchResults.forEach {
            SearchResultEpoxyModel
        }
    }

    data class SearchResultEpoxyModel(
        val searchResultMaster: SearchResultMaster
    ): ViewBindingKotlinModel<VhSearchResultBinding>(R.layout.vh_search_result) {
        override fun VhSearchResultBinding.bind() {
            imgMaster.load(searchResultMaster.img_url)
            rvInfo.setOnClickListener {
                it.translationY = 50f
            }
        }
    }
}