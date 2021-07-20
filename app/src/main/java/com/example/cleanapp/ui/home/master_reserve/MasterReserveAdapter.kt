package com.example.cleanapp.ui.home.master_reserve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseAdapterViewType
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.*
import com.example.cleanapp.models.Master

class MasterReserveAdapter(
    private val master: Master,
    private val moreMasters: MutableList<Master>,
    private val masterReserveClickListener: MasterReserveClickListener
) :
    BaseAdapterViewType<Int>() {

    fun setItems(viewTypeOrder: List<Int>) {
        this.items.clear()
        this.items.addAll(viewTypeOrder)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding> {
        return when (viewType) {
            ReservationViewTypes.HEADER.type -> {
                ViewHolderHeader(
                    VhReserve0HeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ReservationViewTypes.REVIEWS.type -> {
                ViewHolderReviews(
                    VhReserve1ReviewsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ReservationViewTypes.LANGUAGES.type -> {
                ViewHolderLanguages(
                    VhReserve2LanguagesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ReservationViewTypes.INFO_EDIT.type -> {
                ViewHolderInfoEdit(
                    VhReserve3InfoEditBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ReservationViewTypes.REPORT.type -> {
                ViewHolderReport(
                    VhReserve4ReportBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ReservationViewTypes.MORE.type -> {
                ViewHolderMore(
                    VhReserve5MoreBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> ErrorViewHolder(
                VhReserve6ErrorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    inner class ViewHolderHeader(private val binding: VhReserve0HeaderBinding) :
        BaseViewHolder<VhReserve0HeaderBinding>(binding) {
        override fun bind() {

        }

    }

    inner class ViewHolderReviews(private val binding: VhReserve1ReviewsBinding) :
        BaseViewHolder<VhReserve1ReviewsBinding>(binding) {
        override fun bind() {

        }
    }

    inner class ViewHolderLanguages(private val binding: VhReserve2LanguagesBinding) :
        BaseViewHolder<VhReserve2LanguagesBinding>(binding) {
        override fun bind() {

        }
    }

    inner class ViewHolderInfoEdit(private val binding: VhReserve3InfoEditBinding) :
        BaseViewHolder<VhReserve3InfoEditBinding>(binding) {
        override fun bind() {

        }
    }

    inner class ViewHolderReport(private val binding: VhReserve4ReportBinding) :
        BaseViewHolder<VhReserve4ReportBinding>(binding) {
        override fun bind() {

        }
    }

    inner class ViewHolderMore(private val binding: VhReserve5MoreBinding) :
        BaseViewHolder<VhReserve5MoreBinding>(binding) {
        override fun bind() {

        }
    }

    inner class ErrorViewHolder(private val binding: VhReserve6ErrorBinding) :
        BaseViewHolder<VhReserve6ErrorBinding>(binding) {
        override fun bind() {

        }
    }

}