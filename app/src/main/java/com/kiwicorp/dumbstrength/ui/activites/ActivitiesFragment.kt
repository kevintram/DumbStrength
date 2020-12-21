package com.kiwicorp.dumbstrength.ui.activites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.kiwicorp.dumbstrength.EventObserver
import com.kiwicorp.dumbstrength.databinding.FragmentActivitiesBinding
import com.kiwicorp.dumbstrength.ui.activites.ActivitiesFragmentDirections.Companion.toActivityDetailFragment
import com.kiwicorp.dumbstrength.ui.activites.ActivitiesFragmentDirections.Companion.toAddEditActivityFragment
import com.kiwicorp.dumbstrength.ui.chooseactivitycommon.SearchActivityViewModel
import com.kiwicorp.dumbstrength.util.Mode
import com.kiwicorp.dumbstrength.util.closeKeyboard
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
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
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