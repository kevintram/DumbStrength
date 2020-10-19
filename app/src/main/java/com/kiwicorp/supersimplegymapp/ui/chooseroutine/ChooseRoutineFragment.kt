package com.kiwicorp.supersimplegymapp.ui.chooseroutine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.databinding.FragmentChooseRoutineBinding
import com.kiwicorp.supersimplegymapp.ui.chooseroutine.ChooseRoutineFragmentDirections.Companion.toAddEditWorkoutGraph
import com.kiwicorp.supersimplegymapp.util.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseRoutineFragment : Fragment() {
    private lateinit var binding: FragmentChooseRoutineBinding

    private val viewModel: ChooseRoutineViewModel by viewModels()

    private lateinit var adapter: RoutinesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseRoutineBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChooseRoutineFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.skipButton.setOnClickListener {
            findNavController().navigate(toAddEditWorkoutGraph(Mode.ADD, null, null))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigateToAddWorkoutFragment.observe(viewLifecycleOwner, EventObserver { routineId ->
            findNavController().navigate(toAddEditWorkoutGraph(Mode.ADD, null, routineId))
        })
    }

    private fun setupRecyclerView() {
        adapter = RoutinesListAdapter(viewModel)
        binding.routineRecyclerView.adapter = adapter
        viewModel.routines.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}