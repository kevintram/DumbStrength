package com.kiwicorp.dumbstrength.ui.addeditworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.dumbstrength.EventObserver
import com.kiwicorp.dumbstrength.R
import com.kiwicorp.dumbstrength.databinding.FragmentAddEditWorkoutBinding
import com.kiwicorp.dumbstrength.ui.addeditworkout.AddEditWorkoutFragmentDirections.Companion.toActivityDetailFragment
import com.kiwicorp.dumbstrength.ui.addeditworkout.AddEditWorkoutFragmentDirections.Companion.toChooseActivityFragment
import com.kiwicorp.dumbstrength.ui.addeditworkout.AddEditWorkoutFragmentDirections.Companion.toWorkoutsFragment
import com.kiwicorp.dumbstrength.util.Mode
import com.kiwicorp.dumbstrength.util.closeKeyboard
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
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                viewModel.updateWorkoutAndClose()
            }
        } else {
            binding.toolbar.title = "Add Workout"
            args.routineId?.let { viewModel.loadRoutine(it) }
            binding.toolbar.setNavigationOnClickListener { viewModel.insertWorkoutAndClose() }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                viewModel.insertWorkoutAndClose()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = WorkoutEntryWithActivityListAdapter(viewModel)
        binding.entriesRecyclerView.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = 0
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                viewModel.swapEntries(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        }
        val touchHelper = ItemTouchHelper(itemTouchHelperCallback)
        touchHelper.attachToRecyclerView(binding.entriesRecyclerView)
    }

    private fun setupNavigation() {
        viewModel.navigateToChooseActivityFragment.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigate(toChooseActivityFragment())
        })
        viewModel.navigateToActivityDetailFragment.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigate(toActivityDetailFragment(it))
        })
        viewModel.close.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigateUp()
        })
    }
}