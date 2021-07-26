package com.example.cleanapp.ui.home.confirmation.card

import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.FragmentNewCardBinding
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order

class NewCardFragment : BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {
private lateinit var master:Master
private lateinit var order:Order

    override fun start() {
        order = arguments?.getParcelable<Order>("order")!!
        master = arguments?.getParcelable<Master>("master")!!
        initRecycler()
        observes()
    }

    private fun initRecycler(){

    }

    private fun observes(){

    }

}