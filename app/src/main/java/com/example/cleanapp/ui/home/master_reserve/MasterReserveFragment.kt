package com.example.cleanapp.ui.home.master_reserve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.R
import com.example.cleanapp.databinding.FragmentReserveBinding
import com.example.cleanapp.models.Master

class MasterReserveFragment : Fragment() {

    private var _binding: FragmentReserveBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: MasterReserveController

    private lateinit var master: Master
    private lateinit var moremasters: MutableList<Master>

    private val viewModel: MasterReserveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReserveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        init()
    }

    private fun setData() {
        master = Master(null, null, null)
        moremasters = mutableListOf()

    }

    private fun init() {
        controller =
            MasterReserveController(master, moremasters, object : MasterReserveClickListener {
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

        binding.controller.setController(controller)
        binding.controller.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        with(binding) {
            tvPrice.text = getString(R.string.per_hour)
//            tvPrice.setTextById(R.string.per_hour)
            tvDateTime.text = "TODO"
            btnReserve.setOnClickListener {
                findNavController().navigate(R.id.action_masterReserveFragment_to_confirmationFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}