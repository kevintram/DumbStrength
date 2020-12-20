package com.kiwicorp.supersimplegymapp.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.R
import com.kiwicorp.supersimplegymapp.databinding.FragmentWorkoutsBinding
import com.kiwicorp.supersimplegymapp.ui.workouts.WorkoutsFragmentDirections.Companion.toAddEditWorkoutGraph
import com.kiwicorp.supersimplegymapp.ui.workouts.WorkoutsFragmentDirections.Companion.toChooseRoutineFragment
import com.kiwicorp.supersimplegymapp.ui.workouts.WorkoutsFragmentDirections.Companion.toWorkoutCalendarFragment
import com.kiwicorp.supersimplegymapp.util.Mode
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WorkoutsFragment: Fragment() {

    private lateinit var binding: FragmentWorkoutsBinding

    private lateinit var adapter: WorkoutsListAdapter

    private val viewModel: WorkoutsViewModel by activityViewModels()

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
        binding.toolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener if (it.itemId == R.id.menu_item_workout_calendar) {
                viewModel.navigateToWorkoutCalendarFragment()
                true
            } else {
                false
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupRecyclerView() {
        adapter = WorkoutsListAdapter(viewModel)
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.workoutsRecyclerView.adapter = adapter
        viewModel.workouts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.scrollToDate.observe(viewLifecycleOwner, EventObserver { date ->
            binding.workoutsRecyclerView.layoutManager?.scrollToPosition(
                adapter.currentList.indexOfFirst { it.workout.date == date }
            )
        })
    }

    private fun setupNavigation() {
        viewModel.navigateToChooseRoutineFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toChooseRoutineFragment())
        })
        viewModel.navigateToEditWorkoutFragment.observe(viewLifecycleOwner, EventObserver { workoutId ->
            findNavController().navigate(toAddEditWorkoutGraph(Mode.EDIT,workoutId, null))
        })
        viewModel.navigateToWorkoutCalendarFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toWorkoutCalendarFragment())
        })
    }
}