package com.example.cleanapp.ui.collect_details.room

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.RoomChooserFragmentBinding
import com.example.cleanapp.extension.toDateFormat

class RoomChooserFragment :
    BaseFragment<RoomChooserFragmentBinding>(RoomChooserFragmentBinding::inflate) {

    private val viewModel: RoomChooserViewModel by viewModels()
    private lateinit var adapter: RoomCounterAdapter

    override fun start() {
        init()
    }

    private fun init() {
        initRecycler()
        val date:Long = requireArguments().getLong("date")
        binding.tvDate.text = date.toDateFormat("MMMM dd, hh:mm aaa")
    }

    private fun initRecycler() {
        adapter = RoomCounterAdapter().apply {
            setItems(viewModel.roomCounters)

            increaseClick = { position ->
                ++viewModel.roomCounters[position].count
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