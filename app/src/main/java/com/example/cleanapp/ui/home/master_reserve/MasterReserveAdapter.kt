package com.example.cleanapp.ui.home.master_reserve

import android.os.Build
import androidx.annotation.RequiresApi
import com.airbnb.epoxy.EpoxyController
import com.example.cleanapp.R
import com.example.cleanapp.databinding.*
import com.example.cleanapp.epoxy.LoadingEpoxyModel
import com.example.cleanapp.epoxy.ViewBindingKotlinModel
import com.example.cleanapp.extensions.load
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.extensions.setTextById
import com.example.cleanapp.extensions.setTextPluralsById
import com.example.cleanapp.models.Master

class MasterReserveController(
    private val master: Master,
    private val moreMasters: MutableList<Master>,
    private val masterReserveClickListener: MasterReserveClickListener
) : EpoxyController() {


    companion object {
        const val INFO_EDIT_AVAILABILITY = 0
        const val INFO_EDIT_CANCELLATION = 1
    }

    private var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    private var items = mutableListOf<Int>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }


    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }
        if (items.isEmpty()) {
            //todo show empty state
            return
        }
        HeaderEpoxyModel(master).addTo(this)
        ReviewsEpoxyModel(master, masterReserveClickListener).addTo(this)
        LanguagesEpoxyModel(master, masterReserveClickListener).addTo(this)
        InfoEdit(master, masterReserveClickListener, INFO_EDIT_AVAILABILITY).addTo(this)
        InfoEdit(master, masterReserveClickListener, INFO_EDIT_CANCELLATION).addTo(this)
        ReportEpoxyModel(master, masterReserveClickListener).addTo(this)
        MoreEpoxyModel(moreMasters).addTo(this)

    }


    data class HeaderEpoxyModel(val master: Master) :
        ViewBindingKotlinModel<VhReserve0HeaderBinding>(
            R.layout.vh_reserve_0_header
        ) {
        override fun VhReserve0HeaderBinding.bind() {
            imgMaster.load(master.imgUrl)
            tvName.text = master.name
        }
    }

    data class ReviewsEpoxyModel(
        val master: Master,
        val masterReserveClickListener: MasterReserveClickListener
    ) :
        ViewBindingKotlinModel<VhReserve1ReviewsBinding>(
            R.layout.vh_reserve_1_reviews
        ) {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun VhReserve1ReviewsBinding.bind() {
            val stars = master.rating
            val nReviews = master.n_reviews
            tvReviews.setResourceHtmlText(R.string.reviews2, stars, nReviews)

            val recentReview = master.recentReviews?.get(0)
            imgAuthor.load(recentReview?.imageUrl)
            tvAuthorName.text = recentReview?.author
            tvCommentDate.text = recentReview?.atDate
            tvComment.text = recentReview?.comment

            btnAllComments.setOnClickListener {
                masterReserveClickListener.onClick(ReservationClickTypes.SHOW_COMMENTS.type)
            }
        }
    }

    data class LanguagesEpoxyModel(
        val master: Master,
        val masterReserveClickListener: MasterReserveClickListener
    ) :
        ViewBindingKotlinModel<VhReserve2LanguagesBinding>(
            R.layout.vh_reserve_2_languages
        ) {
        override fun VhReserve2LanguagesBinding.bind() {
            val languages = master.languages?.joinToString(", ")
            tvLanguages.setTextById(R.string.languages, languages)

            val responseRate = 100
            tvResponseRate.setTextById(R.string.response_rate, responseRate)

            btnContactMaster.setOnClickListener {
                masterReserveClickListener.onClick(ReservationClickTypes.CONTACT_MASTER.type)
            }
        }
    }

    data class InfoEdit(
        val master: Master,
        val masterReserveClickListener: MasterReserveClickListener,
        val infoEditType: Int
    ) :
        ViewBindingKotlinModel<VhReserve3InfoEditBinding>(
            R.layout.vh_reserve_3_info_edit
        ) {
        override fun VhReserve3InfoEditBinding.bind() {
            when (infoEditType) {

                INFO_EDIT_AVAILABILITY -> {
                    tvTitle.setTextById(R.string.availability)

                    //TODO tvDescription.text = master.reservedDate

                    root.setOnClickListener {
                        masterReserveClickListener.onClick(ReservationClickTypes.AVAILABILITY.type)
                    }
                }

                INFO_EDIT_CANCELLATION -> {
                    tvTitle.setTextById(R.string.cancellation)

                    val count = master.cancelPeriod!!
                    tvDescription.setTextPluralsById(R.plurals.cancellation_policy, count)

                    root.setOnClickListener {
                        masterReserveClickListener.onClick(ReservationClickTypes.CANCELLATION.type)
                    }
                }
            }
        }
    }

    data class ReportEpoxyModel(
        val master: Master,
        val masterReserveClickListener: MasterReserveClickListener
    ) :
        ViewBindingKotlinModel<VhReserve4ReportBinding>(
            R.layout.vh_reserve_4_report
        ) {
        override fun VhReserve4ReportBinding.bind() {
            root.setOnClickListener {
                masterReserveClickListener.onClick(ReservationClickTypes.REPORT.type)
            }
        }
    }

    data class MoreEpoxyModel(
        val moreMasters: MutableList<Master>,
    ) :
        ViewBindingKotlinModel<VhReserve5MoreBinding>(
            R.layout.vh_reserve_5_more
        ) {
        override fun VhReserve5MoreBinding.bind() {
        }
    }

}