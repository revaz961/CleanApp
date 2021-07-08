package com.example.cleanapp.ui.collect_details.room

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.RoomChooserFragmentBinding
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.ui.collect_details.ChooserViewModel

class RoomChooserFragment :
    BaseFragment<RoomChooserFragmentBinding>(RoomChooserFragmentBinding::inflate) {

    private val viewModel: RoomChooserViewModel by viewModels()
    private val chooserViewModel: ChooserViewModel by activityViewModels()
    private lateinit var adapter: RoomCounterAdapter

    override fun start() {
        init()
    }

    private fun init() {
        initRecycler()
        val date: Long = requireArguments().getLong("date")
        binding.tvDate.text = date.toDateFormat("MMMM dd, hh:mm aaa")
        binding.btnApply.setOnClickListener {
            chooserViewModel.setRoomCount(viewModel.roomCounters)
            chooserViewModel.setOrderInDb()
        }
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
    }

}