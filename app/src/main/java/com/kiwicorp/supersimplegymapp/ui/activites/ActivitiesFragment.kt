package com.kiwicorp.supersimplegymapp.ui.activites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.MainActivity
import com.kiwicorp.supersimplegymapp.databinding.FragmentActivitiesBinding
import com.kiwicorp.supersimplegymapp.ui.activites.ActivitiesFragmentDirections.Companion.toActivityDetailFragment
import com.kiwicorp.supersimplegymapp.ui.activites.ActivitiesFragmentDirections.Companion.toAddEditActivityFragment
import com.kiwicorp.supersimplegymapp.ui.addeditactivity.AddEditActivityFragment
import com.kiwicorp.supersimplegymapp.ui.addeditworkout.AddEditWorkoutFragment
import com.kiwicorp.supersimplegymapp.ui.chooseactivitycommon.SearchActivityViewModel
import com.kiwicorp.supersimplegymapp.util.Mode
import com.kiwicorp.supersimplegymapp.util.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {

    private lateinit var binding: FragmentActivitiesBinding

    private lateinit var adapter: ActivitiesListAdapter

    private val viewModel: ActivitiesViewModel by viewModels()

    private val searchViewModel: SearchActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ActivitiesFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupRecyclerView() {
        adapter = ActivitiesListAdapter(viewModel)
        binding.activitiesRecyclerView.adapter = adapter
        searchViewModel.activities.observe(viewLifecycleOwner, Observer {
            adapter.addHeadersAndSubmitList(it)
        })
    }

    private fun setupSearchView() {
        with(binding.searchView) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchViewModel.onSearchQueryChanged(newText)
                    return true
                }
            })
        }
    }

    private fun setupNavigation() {
        viewModel.navigateToAddActivityFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toAddEditActivityFragment(Mode.ADD, null))
        })
        viewModel.navigateToActivityDetailFragment.observe(viewLifecycleOwner, EventObserver { activityId ->
            closeKeyboard()
            findNavController().navigate(toActivityDetailFragment(activityId))
        })
    }

}