package com.example.cleanapp.ui.home.confirmation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ConfirmationFragmentBinding
import com.example.cleanapp.databinding.FragmentNewCardBinding
import com.example.cleanapp.extensions.init
import com.example.cleanapp.models.Card
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.ConfirmationViewTypes

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
            }
            confirm = {
                viewModel.confirmOrder(order)
            }
            setCurrentCard = {
                viewModel.currentCard = it
            }
            setItems(
                ConfirmationViewTypes.toTypeList()
            )
        }

        binding.rvConfirmation.adapter = adapter
        binding.rvConfirmation.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun observes() {
//        viewModel.cardsLiveData.observe(viewLifecycleOwner, {
//            when (it) {
//                is ResultHandler.Success -> {
//                    adapter.setCards(it.data!!)
//                }
//            }
//        })
    }

    private fun createCard() {
        showDialog()
    }

    private fun showDialog() {
        val bindingDialog = FragmentNewCardBinding.inflate(layoutInflater)
        val cardDialog = Dialog(requireContext())
        cardDialog.init(bindingDialog.root,
            width = ViewGroup.LayoutParams.MATCH_PARENT)

        //todo set listeners

        cardDialog.show()
        with(cardDialog) {

//            window?.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )

            window?.attributes?.windowAnimations = R.style.DialogAnimation
            window?.setGravity(Gravity.BOTTOM)
            bindingDialog.btnClose.setOnClickListener {
                val card = Card()
                viewModel.addCard(card)
            }
        }
    }
}