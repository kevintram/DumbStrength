package com.kiwicorp.supersimplegymapp.ui.addeditroutine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.kiwicorp.supersimplegymapp.AddEditRoutineGraphDirections.Companion.toRoutinesFragment
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.R
import com.kiwicorp.supersimplegymapp.databinding.FragmentAddEditRoutineBinding
import com.kiwicorp.supersimplegymapp.ui.addeditroutine.AddEditRoutineFragmentDirections.Companion.toChooseActivityFragment
import com.kiwicorp.supersimplegymapp.util.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditRoutineFragment : Fragment() {
    private lateinit var binding: FragmentAddEditRoutineBinding

    private val viewModel: AddEditRoutineViewModel by navGraphViewModels(R.id.addEditRoutineGraph) {
        defaultViewModelProviderFactory
    }

    private lateinit var adapter: RoutineEntryWithActivityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditRoutineBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddEditRoutineFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.toolbar.setNavigationOnClickListener {
            viewModel.insertRoutineAndClose()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupRecyclerView() {
        adapter = RoutineEntryWithActivityListAdapter(viewModel)
        binding.entriesRecyclerView.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupNavigation() {
        viewModel.close.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigate(toRoutinesFragment())
        })
        viewModel.navigateToChooseActivityFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toChooseActivityFragment())
        })
    }
}