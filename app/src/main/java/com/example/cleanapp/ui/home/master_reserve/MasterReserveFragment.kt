package com.example.cleanapp.ui.home.master_reserve

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.R
import com.example.cleanapp.databinding.FragmentReserveBinding
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.utils.ReservationClickTypes
import com.example.cleanapp.utils.ReservationViewTypes

class MasterReserveFragment : Fragment() {

    private var _binding: FragmentReserveBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MasterReserveAdapter
    private lateinit var order: Order

    private lateinit var master: Master
    private lateinit var moremasters: MutableList<Master>

    private val viewTypeOrder = listOf(
        ReservationViewTypes.HEADER.type,
        ReservationViewTypes.REVIEWS.type,
        ReservationViewTypes.LANGUAGES.type,
        ReservationViewTypes.INFO_EDIT.type,
        ReservationViewTypes.CANCELLATION.type,
        ReservationViewTypes.REPORT.type,
        ReservationViewTypes.MORE.type
    )

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
        master = arguments?.getParcelable("master") ?: Master()
        moremasters = mutableListOf(master, master, master, master)


    }

    private fun init() {
        order = arguments?.getParcelable<Order>("order")!!

        d("MASTER", master.toString())
        adapter =
            MasterReserveAdapter(master, moremasters, order, object : MasterReserveClickListener {
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
        adapter.setItems(viewTypeOrder)

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        with(binding) {
            master.categories?.get(0)
                ?.let { tvPrice.setResourceHtmlText(R.string.per_hour, it.price) }
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