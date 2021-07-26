package com.example.cleanapp.ui.home.confirmation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.fragment.app.viewModels
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ConfirmationFragmentBinding
import com.example.cleanapp.databinding.FragmentNewCardBinding
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler

class ConfirmationFragment : BaseFragment<ConfirmationFragmentBinding>(ConfirmationFragmentBinding::inflate) {

    private val viewModel: ConfirmationViewModel by viewModels()
    private lateinit var adapter: ConfirmationAdapter

    override fun start() {
        val order = arguments?.getParcelable<Order>("order")!!
        val master = arguments?.getParcelable<Master>("master")!!
        initRecycler(master, order)
        observes()
    }

    private fun initRecycler(master : Master, order : Order) {
        adapter = ConfirmationAdapter(master, order).apply {
            sendMessage = {
                viewModel.sendMessage(it)
            }
            addCard = {
                createCard()
                viewModel.addCard(it)
            }
            confirm = {
                viewModel.confirmOrder(order)
            }
            setCurrentCard = {
                viewModel.currentCard = it
            }
        }
    }

    private fun observes() {
        viewModel.cardsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setCards(it.data!!)
                }
            }
        })
    }

    private fun createCard() {
        showDialog()
    }

    private fun showDialog() {
        val cardDialog = Dialog(requireContext())
        cardDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        cardDialog.setContentView(R.layout.fragment_new_card)

        //todo set listeners

        cardDialog.show()
        with(cardDialog) {

            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.attributes?.windowAnimations = R.style.DialogAnimation
            window?.setGravity(Gravity.BOTTOM)
        }
    }
}