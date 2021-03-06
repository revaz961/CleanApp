package com.example.cleanapp.ui.collect_details.room

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.RoomChooserFragmentBinding
import com.example.cleanapp.extensions.gone
import com.example.cleanapp.extensions.goneIf
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomChooserFragment :
    BaseFragment<RoomChooserFragmentBinding>(RoomChooserFragmentBinding::inflate) {

    private val viewModel: RoomChooserViewModel by viewModels()
    private val sharedViewModel: ChooserViewModel by activityViewModels()
    private lateinit var adapter: RoomCounterAdapter
    private lateinit var order: Order

    override fun start() {
        init()
    }

    private fun init() {
        order = arguments?.getParcelable("order") ?: Order()

        binding.tvDate.text = order.date!!.toDateFormat("MMMM dd, hh:mm aaa")

        observes()

        initRecycler()

        setListeners()
    }

    private fun initRecycler() {
        adapter = RoomCounterAdapter().apply {

            setItems(viewModel.roomCounters)

            increaseClick = { position ->
                if (viewModel.roomCounters[position].count < 10)
                    ++viewModel.roomCounters[position].count
                else
                    viewModel.roomCounters[position].count
            }

            decreaseClick = { position ->
                if (viewModel.roomCounters[position].count > 0)
                    --viewModel.roomCounters[position].count
                else
                    0
            }
        }

        binding.rvRoom.adapter = adapter
        binding.rvRoom.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getRooms()
    }

    private fun setListeners() {

        binding.btnApply.setOnClickListener {
            order.roomCount = viewModel.roomCounters.filter { it.count != 0 }
            if (!order.roomCount.isNullOrEmpty()) {
                order.duration = order.roomCount?.fold(0) { acc, roomCounter ->
                    acc + roomCounter.count * order.category!!.categoryDuration
                }
                sharedViewModel.setFragmentTitle(getString(R.string.city_title))
                findNavController().navigate(
                    R.id.action_roomChooserFragment_to_cityChooserFragment,
                    bundleOf("order" to order)
                )
            } else showErrorDialog("Choose at least one room")
        }

        binding.btnBack.setOnClickListener {
            sharedViewModel.setFragmentTitle(getString(R.string.date_title))
            findNavController().navigateUp()
        }
    }

    private fun observes() {
        viewModel.roomLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setItems(it.data!!)
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
    }
}