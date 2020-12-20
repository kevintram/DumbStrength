package com.kiwicorp.supersimplegymapp.ui.addeditworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.kiwicorp.supersimplegymapp.R
import com.kiwicorp.supersimplegymapp.databinding.FragmentChooseActivityBinding
import com.kiwicorp.supersimplegymapp.ui.addeditroutine.ChooseActivityFragmentDirections
import com.kiwicorp.supersimplegymapp.ui.addeditworkout.ChooseActivityFragmentDirections.Companion.toAddEditActivityFragment
import com.kiwicorp.supersimplegymapp.ui.chooseactivitycommon.ChooseActivityListAdapter
import com.kiwicorp.supersimplegymapp.ui.chooseactivitycommon.SearchActivityViewModel
import com.kiwicorp.supersimplegymapp.util.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseActivityFragment : Fragment() {

    private lateinit var binding: FragmentChooseActivityBinding

    // must pass defaultViewModelProviderFactory https://github.com/google/dagger/issues/1935
    private val viewModel: AddEditWorkoutViewModel by navGraphViewModels(R.id.addEditWorkoutGraph) {
        defaultViewModelProviderFactory
    }

    private val searchViewModel: SearchActivityViewModel by viewModels()

    private lateinit var adapter: ChooseActivityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseActivityBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupAppbar()
    }

    private fun setupRecyclerView() {
        adapter = ChooseActivityListAdapter(viewModel)
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.activityRecyclerView.adapter = adapter
        searchViewModel.activities.observe(viewLifecycleOwner, Observer {
            adapter.addHeadersAndSubmitList(it)
        })
    }

    private fun setupAppbar() {
        setupSearchView()
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener if(it.itemId == R.id.menu_item_add_activity) {
                findNavController().navigate(toAddEditActivityFragment(Mode.ADD, null))
                true
            } else {
                false
            }
        }
    }

    private fun setupSearchView() {
        with(binding.searchView) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchViewModel.onSearchQueryChanged(newText)
                    return true
                }
            })
        }
    }
}