package com.kiwicorp.supersimplegymapp.ui.addeditworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.NavGraphDirections.Companion.toWorkoutsFragment
import com.kiwicorp.supersimplegymapp.R
import com.kiwicorp.supersimplegymapp.databinding.FragmentAddEditWorkoutBinding
import com.kiwicorp.supersimplegymapp.ui.addeditworkout.AddEditWorkoutFragmentDirections.Companion.toChooseActivityFragment
import com.kiwicorp.supersimplegymapp.util.Mode
import com.kiwicorp.supersimplegymapp.util.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditWorkoutFragment: Fragment() {

    private lateinit var binding: FragmentAddEditWorkoutBinding

    private val args: AddEditWorkoutFragmentArgs by navArgs()

    // must pass defaultViewModelProviderFactory https://github.com/google/dagger/issues/1935
    private val viewModel: AddEditWorkoutViewModel by navGraphViewModels(R.id.addEditWorkoutGraph) {
        defaultViewModelProviderFactory
    }

    private lateinit var adapter: WorkoutEntryWithActivityListAdapter

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
        setupAccordingToMode()
        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupAccordingToMode() {
        if (args.mode == Mode.EDIT) {
            viewModel.loadWorkout(args.workoutId!!)

            binding.toolbar.title = "Edit Workout"
            binding.toolbar.inflateMenu(R.menu.edit_workout_menu)
            binding.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_item_delete_workout -> {
                        viewModel.deleteWorkoutAndClose()
                        true
                    }
                    else -> false
                }
            }
            binding.toolbar.setNavigationOnClickListener { viewModel.updateWorkoutAndClose() }
        } else {
            binding.toolbar.title = "Add Workout"
            binding.toolbar.setNavigationOnClickListener { viewModel.insertWorkoutAndClose() }
        }
    }

    private fun setupRecyclerView() {
        adapter = WorkoutEntryWithActivityListAdapter(viewModel)
        binding.entriesRecyclerView.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupNavigation() {
        viewModel.navigateToChooseActivityFragment.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigate(toChooseActivityFragment())
        })
        viewModel.close.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigate(toWorkoutsFragment())
        })
    }
}