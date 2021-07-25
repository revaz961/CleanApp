package com.example.cleanapp.ui.home.master_reserve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseAdapterViewType
import com.example.cleanapp.base.BaseViewHolderType
import com.example.cleanapp.databinding.*
import com.example.cleanapp.extensions.load
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.ui.home.master_results.MasterAdapter
import com.example.cleanapp.ui.home.master_results.MasterClickListener
import com.example.cleanapp.utils.ReservationViewTypes


class MasterReserveAdapter(
    private var selectedMaster: Master,
    private val moreMasters: MutableList<Master>,
    private val order: Order,
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
    ): BaseViewHolderType<ViewBinding> {
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
            ReservationViewTypes.CANCELLATION.type -> {
                ViewHolderCancellation(
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
        BaseViewHolderType<VhReserve0HeaderBinding>(binding) {
        override fun bind() {
            binding.rvCaegories.adapter = MasterCategoryAdapter().apply {
                setItem(selectedMaster.categories!!)
            }
            binding.rvCaegories.layoutManager = LinearLayoutManager(binding.root.context)

            binding.tvName.text = selectedMaster.user?.name ?: ""
            binding.imgMaster.load(selectedMaster.user?.imgUrl ?: "")
        }
    }

    inner class ViewHolderReviews(private val binding: VhReserve1ReviewsBinding) :
        BaseViewHolderType<VhReserve1ReviewsBinding>(binding) {
        override fun bind() {
            binding.tvRatingName.setResourceHtmlText(
                R.string.reviews,
                selectedMaster.rating,
                selectedMaster.nReviews
            )
            selectedMaster.lastComments?.get(0)?.let {
                binding.tvMasterName.text = it.author ?: ""
                binding.imgAuthor.load(it.imageUrl)
                binding.tvCommentDate.text = it.dateAt?.toDateFormat("MMMM YYYY") ?: ""
                binding.tvComment.text = it.comment
            }
        }
    }

    inner class ViewHolderLanguages(private val binding: VhReserve2LanguagesBinding) :
        BaseViewHolderType<VhReserve2LanguagesBinding>(binding) {
        override fun bind() {
            binding.tvLanguages.setResourceHtmlText(
                R.string.languages,
                selectedMaster.languages?.fold("") { acc, s ->
                    "$acc, $s"
                }?.drop(1) ?: "Georgian"
            )
        }
    }

    inner class ViewHolderInfoEdit(private val binding: VhReserve3InfoEditBinding) :
        BaseViewHolderType<VhReserve3InfoEditBinding>(binding) {
        override fun bind() {
            binding.tvTitle.setText(R.string.availability)
            binding.tvDescription.text = order.date?.toDateFormat("MMMM dd, K:mm a")
        }
    }

    inner class ViewHolderCancellation(private val binding: VhReserve3InfoEditBinding) :
        BaseViewHolderType<VhReserve3InfoEditBinding>(binding) {
        override fun bind() {
            binding.tvTitle.setText(R.string.cancellation_policy)
            val cancellationDate =
                order.date!! + selectedMaster.cancelPeriod!! * 60 * 60 * 24 * 1000
            binding.tvDescription.text = cancellationDate.toDateFormat("MMMM dd, K:mm a")
        }
    }

    inner class ViewHolderReport(private val binding: VhReserve4ReportBinding) :
        BaseViewHolderType<VhReserve4ReportBinding>(binding) {
        override fun bind() {

        }
    }

    inner class ViewHolderMore(private val binding: VhReserve5MoreBinding) :
        BaseViewHolderType<VhReserve5MoreBinding>(binding) {
        override fun bind() {
            val adapter = MasterAdapter(object : MasterClickListener {
                override fun onClick(master: Master) {
                    moreMasters.remove(master)
                    moreMasters.add(selectedMaster)
                    selectedMaster = master
                    notifyDataSetChanged()
                }
            })

            adapter.setItems(moreMasters)

            binding.rvMoreMasters.adapter = adapter
            binding.rvMoreMasters.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    inner class ErrorViewHolder(private val binding: VhReserve6ErrorBinding) :
        BaseViewHolderType<VhReserve6ErrorBinding>(binding) {
        override fun bind() {

        }
    }

}