package com.kiwicorp.supersimplegymapp.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.databinding.FragmentRoutinesBinding
import com.kiwicorp.supersimplegymapp.ui.routinecommon.RoutinesListAdapter
import com.kiwicorp.supersimplegymapp.ui.routines.RoutinesFragmentDirections.Companion.toAddEditRoutineGraph
import com.kiwicorp.supersimplegymapp.util.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutinesFragment : Fragment() {
    private lateinit var binding: FragmentRoutinesBinding

    private val viewModel: RoutinesViewModel by viewModels()

    private lateinit var adapter: RoutinesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoutinesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RoutinesFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    override fun onStop() {
        viewModel.updateRoutineOrder()
        super.onStop()
    }

    private fun setupRecyclerView() {
        adapter = RoutinesListAdapter(viewModel)
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.routineRecyclerView.adapter = adapter
        viewModel.routines.observe(viewLifecycleOwner, Observer {
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
                viewModel.swapRoutines(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        }
        val touchHelper = ItemTouchHelper(itemTouchHelperCallback)
        touchHelper.attachToRecyclerView(binding.routineRecyclerView)
    }

    private fun setupNavigation() {
        viewModel.navigateToAddRoutineFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toAddEditRoutineGraph(Mode.ADD, null))
        })
        viewModel.navigateToEditRoutineFragment.observe(viewLifecycleOwner, EventObserver {routineId ->
            findNavController().navigate(toAddEditRoutineGraph(Mode.EDIT, routineId))
        })
    }
}