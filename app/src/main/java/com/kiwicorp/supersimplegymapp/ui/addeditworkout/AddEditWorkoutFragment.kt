package com.kiwicorp.supersimplegymapp.ui.addeditworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.R
import com.kiwicorp.supersimplegymapp.databinding.FragmentAddEditWorkoutBinding
import com.kiwicorp.supersimplegymapp.ui.addeditworkout.AddEditWorkoutFragmentDirections.Companion.toChooseActivityFragment
import com.kiwicorp.supersimplegymapp.ui.addeditworkout.ChooseActivityFragmentDirections.Companion.toWorkoutsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditWorkoutFragment: Fragment() {

    private lateinit var binding: FragmentAddEditWorkoutBinding

    // must pass defaultViewModelProviderFactory https://github.com/google/dagger/issues/1935
    private val viewModel: AddEditWorkoutViewModel by navGraphViewModels(R.id.addEditWorkoutGraph) {
        defaultViewModelProviderFactory
    }

    private lateinit var adapter: EntryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditWorkoutBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddEditWorkoutFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.toolbar.setNavigationOnClickListener { viewModel.insertWorkoutAndClose() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupRecyclerView() {
        adapter = EntryListAdapter(viewModel)
        binding.entriesRecyclerView.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupNavigation() {
        viewModel.navigateToChooseActivityFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toChooseActivityFragment())
        })
        viewModel.close.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toWorkoutsFragment())
        })
    }
}