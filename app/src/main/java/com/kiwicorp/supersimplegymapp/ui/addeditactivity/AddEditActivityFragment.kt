package com.kiwicorp.supersimplegymapp.ui.addeditactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.databinding.FragmentAddEditActivityBinding
import com.kiwicorp.supersimplegymapp.util.RoundedBottomSheetDialogFragment
import com.kiwicorp.supersimplegymapp.util.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditActivityFragment: RoundedBottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddEditActivityBinding

    private val viewModel: AddEditActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditActivityBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddEditActivityFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameText.requestFocus() // opens keyboard
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.close.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigateUp()
        })
    }
}