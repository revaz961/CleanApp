package com.example.cleanapp.base

import android.app.Dialog
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.databinding.ErrorDialogLayoutBinding
import com.example.cleanapp.extensions.init

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate(inflater, container, false)
            start()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun start()

    fun showErrorDialog(message:String){
        val dialogBinding = ErrorDialogLayoutBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.init(dialogBinding.root)
        dialogBinding.tvDescription.text = message
        dialogBinding.btnClose.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }
}