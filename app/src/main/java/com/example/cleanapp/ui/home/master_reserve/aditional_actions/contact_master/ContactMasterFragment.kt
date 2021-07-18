package com.example.cleanapp.ui.home.master_reserve.aditional_actions.contact_master

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cleanapp.R

class ContactMasterFragment : Fragment() {

    companion object {
        fun newInstance() = ContactMasterFragment()
    }

    private lateinit var viewModel: ContactMasterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_master_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactMasterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}