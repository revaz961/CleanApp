package com.example.cleanapp.ui.home.master_reserve

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ContactMasterDialogBinding
import com.example.cleanapp.databinding.FragmentReserveBinding
import com.example.cleanapp.databinding.ReportMasterDialogBinding
import com.example.cleanapp.extensions.*
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.ReservationClickTypes
import com.example.cleanapp.utils.ReservationViewTypes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MasterReserveFragment :
    BaseFragment<FragmentReserveBinding>(FragmentReserveBinding::inflate) {

    private val viewModel: MasterReserveViewModel by viewModels()

    private lateinit var adapter: MasterReserveAdapter
    private lateinit var order: Order

    private lateinit var master: Master
    private var moreMasters = mutableListOf<Master>()

    override fun start() {
        setData()
        init()
        observes()
    }

    private fun setData() {
        master = arguments?.getParcelable("master") ?: Master()
        moreMasters.addAll(arguments?.getParcelableArrayList<Master>("moreMasters")!!.toList())
        moreMasters.remove(master)
    }

    private fun init() {

        order = arguments?.getParcelable("order")!!

        adapter =
            MasterReserveAdapter(master, moreMasters, order).apply {
                navigateClick = { type ->
                    when (type) {
                        ReservationClickTypes.SHOW_COMMENTS.type -> {
                            findNavController().navigate(R.id.action_masterReserveFragment_to_allCommentsFragment)
                        }
                        ReservationClickTypes.CONTACT_MASTER.type -> {
//                            findNavController().navigate(R.id.action_masterReserveFragment_to_contactMasterFragment)
                            showContactDialog()
                        }
                        ReservationClickTypes.AVAILABILITY.type -> {
                            findNavController().navigate(R.id.action_masterReserveFragment_to_availabilityFragment)
                        }
                        ReservationClickTypes.CANCELLATION.type -> {
                            findNavController().navigate(
                                R.id.action_masterReserveFragment_to_cancellationFragment,
                                bundleOf("days" to master.cancelPeriod!!, "date" to order.date!!)
                            )
                        }
                        ReservationClickTypes.REPORT.type -> {
                            showReportDialog()
                        }
                    }
                }

                onSelectMaster = {
                    binding.recycler.scrollToPosition(0)
                }

                setItems(ReservationViewTypes.typeToList())
            }

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        with(binding) {
            fabBack.setOnClickListener {
                findNavController().navigateUp()
            }
            master.categories?.find { it.category?.categoryEn == order.category?.categoryEn }
                ?.let {
                    tvPrice.setResourceHtmlText(R.string.per_hour, it.price)
                    val price = order.duration!!.minuteToHoursFloat() * it.price
                    order.price = price.roundToDecimal()
                }
            tvDateTime.text = order.date?.toDateFormat("MMMM dd, K:mm a")
            btnReserve.setOnClickListener {

                findNavController().navigate(
                    R.id.action_masterReserveFragment_to_confirmationFragment,
                    bundleOf("order" to order, "master" to master)
                )
            }
        }
    }

    private fun showContactDialog() {
        val contactDialog = Dialog(requireContext())
        val dialogBinding = ContactMasterDialogBinding.inflate(layoutInflater)
        contactDialog.init(dialogBinding.root)
        dialogBinding.btnCall.setOnClickListener {
            dialPhoneNumber(master.user!!.phone!!)
        }

        dialogBinding.btnChat.setOnClickListener {
            viewModel.startChat(master)
            contactDialog.cancel()
        }

        dialogBinding.btnClose.setOnClickListener {
            contactDialog.cancel()
        }

        contactDialog.show()
    }

    private fun showReportDialog() {
        val reportDialog = Dialog(requireContext())
        val dialogBinding = ReportMasterDialogBinding.inflate(layoutInflater)
        reportDialog.init(dialogBinding.root)
        dialogBinding.btnCall.setOnClickListener {
            dialPhoneNumber(master.user!!.phone!!)
        }

        dialogBinding.btnReport.setOnClickListener {
            viewModel.reportMaster(master)
            Snackbar.make(binding.root, "User reported successfully", Snackbar.LENGTH_SHORT).show()
            reportDialog.cancel()
            findNavController().navigate(R.id.homeFragment)
        }

        dialogBinding.btnClose.setOnClickListener {
            reportDialog.cancel()
        }

        reportDialog.show()
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun observes() {
        viewModel.chatLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    binding.progress.gone()
                    if (!viewModel.fromChat) {
                        findNavController().navigate(
                            R.id.action_masterReserveFragment_to_chatFragment2,
                            bundleOf("chat" to it.data!!)
                        )
                        viewModel.fromChat = true
                    } else
                        viewModel.fromChat = false
                }
                is ResultHandler.Error -> {
                    binding.progress.gone()
                }
                is ResultHandler.Loading -> {
                    binding.progress.goneIf(it.loading)
                }
            }
        })
    }
}