package com.kiwicorp.supersimplegymapp.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.databinding.FragmentWorkoutsBinding
import com.kiwicorp.supersimplegymapp.ui.addeditworkout.AddEditWorkoutFragment
import com.kiwicorp.supersimplegymapp.ui.workouts.WorkoutsFragmentDirections.Companion.toAddEditWorkoutGraph
import com.kiwicorp.supersimplegymapp.util.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutsFragment: Fragment() {

    private lateinit var binding: FragmentWorkoutsBinding

    private lateinit var adapter: WorkoutsListAdapter

    private val viewModel: WorkoutsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@WorkoutsFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigateToAddWorkoutFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toAddEditWorkoutGraph(Mode.ADD,null))
        })
        viewModel.navigateToEditWorkoutFragment.observe(viewLifecycleOwner, EventObserver { workoutId ->
            findNavController().navigate(toAddEditWorkoutGraph(Mode.EDIT,workoutId))
        })
    }

    private fun setupRecyclerView() {
        adapter = WorkoutsListAdapter(viewModel)
        binding.workoutsRecyclerView.adapter = adapter
        viewModel.workouts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}