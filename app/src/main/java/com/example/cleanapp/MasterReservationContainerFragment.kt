package com.example.cleanapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MasterReservationContainerFragment : Fragment() {

    companion object {
        fun newInstance() = MasterReservationContainerFragment()
    }

    private lateinit var viewModel: MasterReservationContainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.master_reservation_container_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MasterReservationContainerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}