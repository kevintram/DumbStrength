package com.kiwicorp.dumbstrength.ui.addeditactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiwicorp.dumbstrength.EventObserver
import com.kiwicorp.dumbstrength.databinding.FragmentAddEditActivityBinding
import com.kiwicorp.dumbstrength.util.Mode
import com.kiwicorp.dumbstrength.util.RoundedBottomSheetDialogFragment
import com.kiwicorp.dumbstrength.util.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditActivityFragment: RoundedBottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddEditActivityBinding

    private val args: AddEditActivityFragmentArgs by navArgs()

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

        if (args.mode == Mode.EDIT) {
            viewModel.loadActivity(args.activityId!!)
            binding.doneButton.setOnClickListener { viewModel.updateWorkoutAndClose() }
        } else {
            binding.doneButton.setOnClickListener { viewModel.insertWorkoutAndClose() }
        }

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