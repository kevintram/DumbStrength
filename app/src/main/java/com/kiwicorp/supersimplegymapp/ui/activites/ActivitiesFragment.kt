package com.kiwicorp.supersimplegymapp.ui.activites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kiwicorp.supersimplegymapp.databinding.FragmentActivitiesBinding
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

    private fun setupRecyclerView() {
        adapter = ActivitiesListAdapter(viewModel)
        binding.activitiesRecyclerView.adapter = adapter
        viewModel.activities.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}