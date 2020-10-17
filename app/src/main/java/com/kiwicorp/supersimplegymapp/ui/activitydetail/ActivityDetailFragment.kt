package com.kiwicorp.supersimplegymapp.ui.activitydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.R
import com.kiwicorp.supersimplegymapp.databinding.FragmentActivityDetailBinding
import com.kiwicorp.supersimplegymapp.ui.activitydetail.ActivityDetailFragmentDirections.Companion.toAddEditActivityFragment
import com.kiwicorp.supersimplegymapp.ui.addeditactivity.AddEditActivityFragment
import com.kiwicorp.supersimplegymapp.util.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityDetailFragment: Fragment() {
    private lateinit var binding: FragmentActivityDetailBinding

    private val args: ActivityDetailFragmentArgs by navArgs()

    private val viewModel: ActivityDetailViewModel by viewModels()

    private val adapter = EntryListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivityDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ActivityDetailFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadActivity(args.activityId)
        setupRecyclerView()
        setupToolbar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupRecyclerView() {
        binding.entryHistoryRecyclerView.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_item_edit_activity -> {
                    viewModel.navigateToEditActivityFragment()
                    true
                }
                R.id.menu_item_delete_activity -> {
                    viewModel.deleteWorkoutAndClose()
                    true
                }
                else -> false
            }
        }
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun setupNavigation() {
        viewModel.navigateToEditActivityFragment.observe(viewLifecycleOwner, EventObserver {activityId ->
            findNavController().navigate(toAddEditActivityFragment(Mode.EDIT,activityId))
        })
        viewModel.close.observe(viewLifecycleOwner, EventObserver { findNavController().navigateUp() })
    }

}