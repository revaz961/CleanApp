package com.example.cleanapp.ui.home.master_reserve.aditional_actions.all_comments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cleanapp.R

class AllCommentsFragment : Fragment() {

    companion object {
        fun newInstance() = AllCommentsFragment()
    }

    private lateinit var viewModel: AllCommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_comments_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllCommentsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}