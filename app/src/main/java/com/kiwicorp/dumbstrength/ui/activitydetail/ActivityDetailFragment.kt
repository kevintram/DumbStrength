package com.kiwicorp.dumbstrength.ui.activitydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiwicorp.dumbstrength.EventObserver
import com.kiwicorp.dumbstrength.R
import com.kiwicorp.dumbstrength.databinding.FragmentActivityDetailBinding
import com.kiwicorp.dumbstrength.ui.activitydetail.ActivityDetailFragmentDirections.Companion.toAddEditActivityFragment
import com.kiwicorp.dumbstrength.util.Mode
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
            adapter.addHeaderAndSubmitList(it,viewModel.activity.value)
        })
        viewModel.activity.observe(viewLifecycleOwner, Observer {
            adapter.addHeaderAndSubmitList(viewModel.entries.value,it)
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