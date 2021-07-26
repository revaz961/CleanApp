package com.example.cleanapp.ui.home.confirmation

import android.app.Dialog
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doOnTextChanged
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        viewModel.cardsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> adapter.setCards(it.data!!)

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> {
                }
            }
        })

        viewModel.cardAddLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setCards(viewModel.cards)
                }

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> {
                }
            }
        })
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
                val holder = binding.etCardHolderInput.text.toString().trim()
                val cardNumber = binding.etCardNumberInput.text.toString().trim()
                val valid = binding.etValidInput.text.toString().trim()
                val cvv = binding.etCvvInput.text.toString().trim()
                    val card = Card(cardNumber, holder, valid, cvv)
                    viewModel.addCard(card)
                    dialog.cancel()

            }
            etCardNumberInput.doOnTextChanged { text, start, before, count ->
                val inputLength = text.toString().length
                if (before == 0 && inputLength == 4 ||
                    inputLength == 9 || inputLength == 14
                ) {

                    binding.etCardNumberInput.setText(text.toString() + " ")

                    val pos = binding.etCardNumberInput.text?.length
                    binding.etCardNumberInput.setSelection(pos!!)

                } else if (before > 0 && (inputLength == 4 ||
                            inputLength == 9 || inputLength == 14)
                ) {
                    binding.etCardNumberInput.setText(
                        binding.etCardNumberInput.text.toString().dropLast(2)
                    )
//                            .substring(0, binding.etCardNumberInput.text
//                                .toString().length - 1))

                    val pos = binding.etCardNumberInput.text?.length
                    binding.etCardNumberInput.setSelection(pos!!)

                }
            }
        }
    }

    private fun validateInput(holder: String, number: String, valid: String, cvv: String): Boolean {
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

        return result
    }

}