package com.example.cleanapp.ui.home.master_reserve

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.FragmentReserveBinding
import com.example.cleanapp.extensions.minuteToHoursFloat
import com.example.cleanapp.extensions.roundToDecimal
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.utils.ReservationClickTypes
import com.example.cleanapp.utils.ReservationViewTypes

class MasterReserveFragment : BaseFragment<FragmentReserveBinding>(FragmentReserveBinding::inflate) {

    private val viewModel: MasterReserveViewModel by viewModels()

    private lateinit var adapter: MasterReserveAdapter
    private lateinit var order: Order

    private lateinit var master: Master
    private var moreMasters = mutableListOf<Master>()


    override fun start() {
        setData()
        init()
    }

    private fun setData() {
        master = arguments?.getParcelable("master") ?: Master()
        moreMasters.addAll(arguments?.getParcelableArrayList<Master>("moreMasters")!!.toList())
        moreMasters.remove(master)
    }

    private fun init() {
        order = arguments?.getParcelable<Order>("order")!!

        adapter =
            MasterReserveAdapter(master, moreMasters, order, object : MasterReserveClickListener {
                override fun onClick(type: Int, subType: Int) {
                    when (type) {
                        ReservationClickTypes.SHOW_COMMENTS.type -> {
                            findNavController().navigate(R.id.action_masterReserveFragment_to_allCommentsFragment)
                        }
                        ReservationClickTypes.CONTACT_MASTER.type -> {
                            findNavController().navigate(R.id.action_masterReserveFragment_to_contactMasterFragment)
                        }
                        ReservationClickTypes.AVAILABILITY.type -> {
                            findNavController().navigate(R.id.action_masterReserveFragment_to_availabilityFragment)
                        }
                        ReservationClickTypes.CANCELLATION.type -> {
                            findNavController().navigate(R.id.action_masterReserveFragment_to_cancellationFragment)
                        }
                        ReservationClickTypes.REPORT.type -> {
                            findNavController().navigate(R.id.action_masterReserveFragment_to_reportFragment)
                        }
                    }

                }
            })
        adapter.setItems(ReservationViewTypes.typeToList())

        adapter.onSelectMaster = {
            binding.recycler.scrollToPosition(0)
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
}