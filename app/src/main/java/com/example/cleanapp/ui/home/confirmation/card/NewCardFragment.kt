package com.example.cleanapp.ui.home.confirmation.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.FragmentNewCardBinding

class NewCardFragment : BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {


    override fun start() {
        val order = arguments?.getParcelable<Order>("order")!!
        val master = arguments?.getParcelable<Master>("master")!!
        initRecycler(master, order)
        observes()
    }