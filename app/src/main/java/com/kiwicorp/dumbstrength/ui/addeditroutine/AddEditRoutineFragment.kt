package com.kiwicorp.dumbstrength.ui.addeditroutine

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
import com.kiwicorp.dumbstrength.databinding.FragmentAddEditRoutineBinding
import com.kiwicorp.dumbstrength.ui.addeditroutine.AddEditRoutineFragmentDirections.Companion.toChooseActivityFragment
import com.kiwicorp.dumbstrength.ui.addeditroutine.AddEditRoutineFragmentDirections.Companion.toRoutinesFragment
import com.kiwicorp.dumbstrength.util.Mode
import com.kiwicorp.dumbstrength.util.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditRoutineFragment : Fragment() {
    private lateinit var binding: FragmentAddEditRoutineBinding

    private val args: AddEditRoutineFragmentArgs by navArgs()

    private val viewModel: AddEditRoutineViewModel by navGraphViewModels(R.id.addEditRoutineGraph) {
        defaultViewModelProviderFactory
    }

    private lateinit var adapter: RoutineEntryWithActivityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditRoutineBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddEditRoutineFragment.viewModel
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

    private fun setupRecyclerView() {
        adapter = RoutineEntryWithActivityListAdapter(viewModel)
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

    private fun setupAccordingToMode() {
        if (args.mode == Mode.EDIT) {
            viewModel.loadRoutine(args.routineId!!)

            binding.toolbar.title = "Edit Routine"
            binding.toolbar.inflateMenu(R.menu.edit_routine_menu)
            binding.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_item_delete_routine-> {
                        viewModel.deleteRoutineAndClose()
                        true
                    }
                    else -> false
                }
            }
            binding.toolbar.setNavigationOnClickListener { viewModel.updateRoutineAndClose() }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                viewModel.updateRoutineAndClose()
            }
        } else {
            binding.toolbar.title = "Add Routine"
            binding.toolbar.setNavigationOnClickListener { viewModel.insertRoutineAndClose() }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                viewModel.insertRoutineAndClose()
            }
        }
    }

    private fun setupNavigation() {
        viewModel.close.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigateUp()
        })
        viewModel.navigateToChooseActivityFragment.observe(viewLifecycleOwner, EventObserver {
            closeKeyboard()
            findNavController().navigate(toChooseActivityFragment())
        })
    }
}