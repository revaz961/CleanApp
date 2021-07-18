package com.example.cleanapp.ui.home.master_reserve

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cleanapp.R
import com.example.cleanapp.databinding.VhReserve1ReviewsBinding

//import com.example.cleanapp.databinding.FragmentReserveBinding

class MasterReserveFragment : Fragment() {

//    private var _binding: FragmentReserveBinding? = null
    private var _binding: VhReserve1ReviewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MasterReserveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = FragmentReserveBinding.inflate(inflater, container, false)
        _binding = VhReserve1ReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stars = 4.9
        val reviews = 5
        val styled = Html.fromHtml(getString(R.string.reviews2, stars, reviews,"" ), FROM_HTML_MODE_LEGACY)
        binding.tvReviews.text = styled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}