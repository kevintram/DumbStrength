package com.kiwicorp.supersimplegymapp.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.databinding.FragmentRoutinesBinding
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
        viewModel.navigateToAddActivityFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toAddEditRoutineGraph(Mode.ADD, null))
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