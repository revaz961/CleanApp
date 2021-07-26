package com.example.cleanapp.ui.home.confirmation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseAdapterViewType
import com.example.cleanapp.base.BaseViewHolderType
import com.example.cleanapp.databinding.*
import com.example.cleanapp.extensions.*
import com.example.cleanapp.models.Card
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.Order
import com.example.cleanapp.utils.ConfirmationViewTypes
import java.util.*

class ConfirmationAdapter(
    private val master: Master,
    private val order: Order,
) :
    BaseAdapterViewType<Int>() {

    lateinit var addCard: () -> Unit
    lateinit var sendMessage: (String) -> Unit
    lateinit var confirm: () -> Unit
    lateinit var setCurrentCard: (Card) -> Unit
    private val cards = mutableListOf<Card>()

    fun setItems(viewTypeOrder: List<Int>) {
        this.items.clear()
        this.items.addAll(viewTypeOrder)
        notifyDataSetChanged()
    }

    fun setCards(cards: List<Card>) {
        this.cards.addAll(cards)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolderType<ViewBinding> {
        return when (viewType) {
            ConfirmationViewTypes.HEADER.type -> {
                ViewHolderHeader(
                    VhConfirm0HeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ConfirmationViewTypes.ARRIVAL_DETAILS.type -> {
                ViewHolderArrival(
                    VhConfirm1ArrivalDetailsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ConfirmationViewTypes.PRICE_DETAILS.type -> {
                ViewHolderPrice(
                    VhConfirm2PriceDetailsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ConfirmationViewTypes.CREDIT_CARDS.type -> {
                ViewHolderCards(
                    VhConfirm3PayWithBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ConfirmationViewTypes.MESSAGE_MASTER.type -> {
                ViewHolderMessage(
                    VhConfirm4MessageMasterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ConfirmationViewTypes.CANCELLATION.type -> {
                ViewHolderCancel(
                    VhConfirm5CancellationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ConfirmationViewTypes.FOOTER.type -> {
                ViewHolderFooter(
                    VhConfirm6FooterBinding.inflate(
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

    inner class ViewHolderHeader(private val binding: VhConfirm0HeaderBinding) :
        BaseViewHolderType<VhConfirm0HeaderBinding>(binding) {
        override fun bind() {
            with(binding) {
                imageView.load(master.user?.imgUrl)
                tvRooms.text = order.roomCount?.fold("") { acc, roomCounter ->
                    "$acc\n${roomCounter.room} - ${roomCounter.count}"
                }
                tvRatingName.setTextById(R.string.rating_name, master.rating, master.user?.name)
            }
        }
    }

    inner class ViewHolderArrival(private val binding: VhConfirm1ArrivalDetailsBinding) :
        BaseViewHolderType<VhConfirm1ArrivalDetailsBinding>(binding) {
        override fun bind() {
            with(binding) {
                tvDateTimeValue.text = order.date?.toDateFormat("MMMM dd, K:mm a") ?: "error"
                tvAddressValue.text = order.address
            }
        }
    }

    inner class ViewHolderPrice(private val binding: VhConfirm2PriceDetailsBinding) :
        BaseViewHolderType<VhConfirm2PriceDetailsBinding>(binding) {
        override fun bind() {
            with(binding) {
                tvCatPriceValue.setTextById(R.string.price_value, order.price)
                tvDurationValue.setTextById(R.string.n_hours, order.duration)
                val cleaningPrice = order.price * order.duration!!
                tvCleaningPriceValue.setTextById(R.string.price_value, cleaningPrice)
                val serviceFee = cleaningPrice * 0.18
                tvServiceFee.setTextById(R.string.price_value, serviceFee)
                val total = cleaningPrice + serviceFee
                tvTotalValue.setTextById(R.string.price_value, total)
            }
        }
    }

    inner class ViewHolderCards(private val binding: VhConfirm3PayWithBinding) :
        BaseViewHolderType<VhConfirm3PayWithBinding>(binding) {
        override fun bind() {
            binding.tvAddCard.setOnClickListener {
                addCard()
            }
            binding.rvCards.adapter = CardsAdapter().apply {
                setItem(cards)
                selectCard = {
                    binding.root.setBorder(1, "#000000", 20f)
                    setCurrentCard(it)
                }
            }
            binding.rvCards.layoutManager = LinearLayoutManager(binding.root.context)
        }
    }

    inner class ViewHolderMessage(private val binding: VhConfirm4MessageMasterBinding) :
        BaseViewHolderType<VhConfirm4MessageMasterBinding>(binding) {
        override fun bind() {
            with(binding) {
                imgAuthor.load(master.user?.imgUrl)
                tvMasterName.text = master.user?.name ?: "error"
                master.user?.let {
                    it.registrationDate?.let { it1 ->
                        tvJoinDate.setTextById(
                            R.string.registration_year,
                            it1.toDateFormat("YYYY")
                        )
                    }
                }
                master.user?.name?.let { it ->
                    etMessage.setTextById(R.string.chat_intro,
                        it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
                }
                btnSend.setOnClickListener {
                    sendMessage(etMessage.text.toString())
                    with(etMessage) {
                        setResourceHtmlText(R.string.message_sent)
                        keyListener = null
                    }
                    with(btnSend) {
                        setTintColor(R.color.gray_dark)
                        isClickable = false
                    }
                }
            }
        }
    }

    inner class ViewHolderCancel(private val binding: VhConfirm5CancellationBinding) :
        BaseViewHolderType<VhConfirm5CancellationBinding>(binding) {
        override fun bind() {
            binding.tvCancellationDetails.setTextById(
                R.string.cancellation_details,
                master.cancelPeriod
            )

        }
    }

    inner class ViewHolderFooter(private val binding: VhConfirm6FooterBinding) :
        BaseViewHolderType<VhConfirm6FooterBinding>(binding) {
        override fun bind() {
            binding.btnConfirm.setOnClickListener {
                confirm()
            }
        }
    }

    inner class ErrorViewHolder(private val binding: VhReserve6ErrorBinding) :
        BaseViewHolderType<VhReserve6ErrorBinding>(binding) {
        override fun bind() {}
    }
}