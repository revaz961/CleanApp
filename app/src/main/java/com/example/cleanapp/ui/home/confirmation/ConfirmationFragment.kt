package com.example.cleanapp.ui.home.confirmation

import android.app.Dialog
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doAfterTextChanged
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
import com.example.cleanapp.utils.ConfirmationViewTypes

class ConfirmationFragment :
    BaseFragment<ConfirmationFragmentBinding>(ConfirmationFragmentBinding::inflate) {

    private val viewModel: ConfirmationViewModel by viewModels()
    private lateinit var adapter: ConfirmationAdapter

    override fun start() {
        val order = arguments?.getParcelable<Order>("order")!!
        val master = arguments?.getParcelable<Master>("master")!!
        initRecycler(master, order)
        observes()
    }

    private fun initRecycler(master: Master, order: Order) {
        adapter = ConfirmationAdapter(master, order).apply {
            sendMessage = {
                viewModel.sendMessage(it)
            }
            addCard = {
                showCardDialog()
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
//        validateInput()

    }


    private fun showCardDialog() {
        val bindingDialog = FragmentNewCardBinding.inflate(layoutInflater)
        val cardDialog = Dialog(requireContext())
        cardDialog.init(
            bindingDialog.root,
            width = ViewGroup.LayoutParams.MATCH_PARENT
        )

        cardDialog.show()
        with(cardDialog) {

            window?.attributes?.windowAnimations = R.style.DialogAnimation
            window?.setGravity(Gravity.BOTTOM)
            bindingDialog.btnClose.setOnClickListener {
                val card = Card()
                viewModel.addCard(card)
            }
        }

        setListeners(bindingDialog, cardDialog)


        // Set error text
//        passwordLayout.error = getString(R.string.error)

// Clear error text
//        passwordLayout.error = null
    }

    private fun setListeners(binding: FragmentNewCardBinding, dialog: Dialog) {
        with(binding) {
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            btnAdd.setOnClickListener {
//                createCard(binding, dialog)
            }
            var count = 0
            etCardNumberInput.doAfterTextChanged {

                val inputLength = it.toString().length
                if (count <= inputLength && inputLength == 4 ||
                    inputLength == 9 || inputLength == 14
                ) {

                    binding.etCardNumberInput.setText(it.toString() + " ")

                    val pos = binding.etCardNumberInput.text?.length
                    binding.etCardNumberInput.setSelection(pos!!)

                } else if (count >= inputLength && (inputLength == 4 ||
                            inputLength == 9 || inputLength == 14)
                ) {
                    binding.etCardNumberInput.setText(
                        binding.etCardNumberInput.text.toString().dropLast(1)
                    )
//                            .substring(0, binding.etCardNumberInput.text
//                                .toString().length - 1))

                    val pos = binding.etCardNumberInput.text?.length
                    binding.etCardNumberInput.setSelection(pos!!)

                }
                count = binding.etCardNumberInput.text.toString().length
            }
        }
    }

    private fun validateInput(holder: String, number: String, valid: String, cvv: String) {
        var result = true
        val num = number.replace(" ", "")
        val validThrough = valid.replace("/", "")

        if (!holder.trim().contains(" ")) {
            result = false
        }
        if (!num.isDigitsOnly() || num.length != 16) {
            result = false
        }
        if (!validThrough.isDigitsOnly() || validThrough.substring(0, 1)
                .toInt() > 12 || validThrough.substring(2).toInt() < 21
        ) {
            result = false
        }
        if (!cvv.isDigitsOnly()) {
            result = false
        }
    }

}