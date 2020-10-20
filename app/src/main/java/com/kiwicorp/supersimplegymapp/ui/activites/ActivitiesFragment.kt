package com.kiwicorp.supersimplegymapp.ui.activites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.databinding.FragmentActivitiesBinding
import com.kiwicorp.supersimplegymapp.ui.activites.ActivitiesFragmentDirections.Companion.toActivityDetailFragment
import com.kiwicorp.supersimplegymapp.ui.activites.ActivitiesFragmentDirections.Companion.toAddEditActivityFragment
import com.kiwicorp.supersimplegymapp.ui.addeditactivity.AddEditActivityFragment
import com.kiwicorp.supersimplegymapp.ui.addeditworkout.AddEditWorkoutFragment
import com.kiwicorp.supersimplegymapp.util.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {

    private lateinit var binding: FragmentActivitiesBinding

    private lateinit var adapter: ActivitiesListAdapter

    private val viewModel: ActivitiesViewModel by viewModels()

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigateToAddActivityFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toAddEditActivityFragment(Mode.ADD, null))
        })
        viewModel.navigateToActivityDetailFragment.observe(viewLifecycleOwner, EventObserver { activityId ->
            findNavController().navigate(toActivityDetailFragment(activityId))
        })
    }

    private fun setupRecyclerView() {
        adapter = ActivitiesListAdapter(viewModel)
        binding.activitiesRecyclerView.adapter = adapter
        viewModel.activities.observe(viewLifecycleOwner, Observer {
            adapter.addHeadersAndSubmitList(it)
        })
    }

}