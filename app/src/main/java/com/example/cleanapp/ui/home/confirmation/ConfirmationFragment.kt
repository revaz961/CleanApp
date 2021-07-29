package com.example.cleanapp.ui.home.confirmation

import android.app.Dialog
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ConfirmationFragmentBinding
import com.example.cleanapp.databinding.FragmentNewCardBinding
import com.example.cleanapp.extensions.gone
import com.example.cleanapp.extensions.goneIf
import com.example.cleanapp.extensions.init
import com.example.cleanapp.models.*
import com.example.cleanapp.utils.ConfirmationViewTypes
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ConfirmationFragment :
    BaseFragment<ConfirmationFragmentBinding>(ConfirmationFragmentBinding::inflate) {

    private val viewModel: ConfirmationViewModel by viewModels()
    private lateinit var adapter: ConfirmationAdapter

    override fun start() {
        binding.fabBack.setOnClickListener {
            findNavController().navigateUp()
        }
        val order = arguments?.getParcelable<Order>("order")!!
        val master = arguments?.getParcelable<Master>("master")!!
        viewModel.getCurrentUser()
        initRecycler(master, order)
        observes()
    }

    private fun initRecycler(master: Master, order: Order) {
        adapter = ConfirmationAdapter(master, order).apply {
            sendMessage = { text ->
                viewModel.getCurrentUser().let {
                    val message = Message(
                        text,
                        it.uid!!,
                        it.name!!,
                        it.imgUrl!!,
                        Date().time,
                        false
                    )
                    viewModel.sendMessage(
                        message,
                        Chat(message),
                        "${it.uid}_${master.user?.uid!!}"
                    )
                } ?: showErrorDialog("User is unknown")
            }
            addCard = {
                showCardDialog()
            }
            confirm = {
                order.masterUid = master.user?.uid
                order.clientUid = viewModel.getUserId()
                order.reservationDate = Date().time
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
        viewModel.getCards()
    }

    private fun observes() {
        viewModel.cardsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setCards(it.data!!)
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.cardAddLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setCards(viewModel.cards)
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.confirmationLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    findNavController().navigate(
                        R.id.action_confirmationFragment_to_orderDetailsFragment2,
                        bundleOf("order" to it.data)
                    )
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.messageSendLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(it.loading)
                }
            }
        })
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
    }


    private fun setListeners(binding: FragmentNewCardBinding, dialog: Dialog) {
        with(binding) {
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            btnAdd.setOnClickListener {
                val number = (binding.etCardNumberInput.text ?: "").toString().replace(" ", "")
                val valid = (binding.etValidInput.text ?: "").toString().replace("/", "")
                val cvv = (binding.etCvvInput.text ?: "").toString()
                val holder = (binding.etCardHolderInput.text ?: "").toString().trim()

                viewModel.addCard(
                    Card(
                        number, holder, valid, cvv
                    )
                )

                dialog.cancel()
            }

            etCardNumberInput.doOnTextChanged { s, start, before, n ->
                validateInput(binding)
                val inputLength = s.toString().length
                if ((inputLength == 4 ||
                            inputLength == 9 || inputLength == 14) && before == 0
                ) {
                    binding.etCardNumberInput.setText(s.toString() + " ")

                    val pos = binding.etCardNumberInput.text?.length
                    binding.etCardNumberInput.setSelection(pos!!)

                } else if ((inputLength == 4 ||
                            inputLength == 9 || inputLength == 14) && before == 1
                ) {
                    binding.etCardNumberInput.setText(
                        binding.etCardNumberInput.text.toString().dropLast(1)
                    )
                    val pos = binding.etCardNumberInput.text?.length
                    binding.etCardNumberInput.setSelection(pos!!)

                }
            }


            etValidInput.doOnTextChanged { s, start, before, n ->
                validateInput(binding)
                val inputLength = s.toString().length
                if (inputLength == 2 && before == 0) {
                    binding.etValidInput.setText(s.toString() + "/")

                    val pos = binding.etValidInput.text?.length
                    binding.etValidInput.setSelection(pos!!)

                } else if (inputLength == 2 && before == 1) {
                    binding.etValidInput.setText(
                        binding.etValidInput.text.toString().dropLast(1)
                    )
                    val pos = binding.etValidInput.text?.length
                    binding.etValidInput.setSelection(pos!!)
                }
            }


            etCardHolderInput.doOnTextChanged { s, start, before, n ->
                validateInput(binding)
            }

        }
    }


    private fun validateInput(binding: FragmentNewCardBinding) {
        var result = true
        val number = (binding.etCardNumberInput.text ?: "").toString().replace(" ", "")
        val valid = (binding.etValidInput.text ?: "").toString().replace("/", "")
        val cvv = (binding.etCvvInput.text ?: "").toString()
        val holder = (binding.etCardHolderInput.text ?: "").toString().trim()

        /** Check card holder name */
        if (!holder.contains(" ") && !holder.matches("^[a-zA-Z]*$".toRegex())) {
            result = false
            binding.etCardHolderInput.error = getString(R.string.error)
        } else
            binding.etCardHolderInput.error = null

        /** Check card number */
        if (number.isEmpty() || !number.isDigitsOnly() || number.length != 16
        ) {
            binding.etCardNumberInput.error = getString(R.string.error)
            result = false
        } else
            binding.etCardNumberInput.error = null

        /** Check card expiration date */
        if (valid.isNotEmpty() && (!valid.isDigitsOnly() || valid.substring(0, 1)
                .toInt() > 12
                    || (valid.length > 2 && valid.substring(2).toInt() < 21))
        ) {
            binding.etValidInput.error = getString(R.string.error)
            result = false
        } else
            binding.etValidInput.error = null

        /** Check card expiration date */
        if (!cvv.isDigitsOnly()) {
            binding.etCvvInput.error = getString(R.string.error)
            result = false
        } else
            binding.etCvvInput.error = null

        binding.btnAdd.isClickable = result
    }
}