package com.kiwicorp.dumbstrength.ui.addeditroutine

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
import com.kiwicorp.dumbstrength.R
import com.kiwicorp.dumbstrength.databinding.FragmentChooseActivityBinding
import com.kiwicorp.dumbstrength.ui.addeditroutine.ChooseActivityFragmentDirections.Companion.toAddEditActivityFragment
import com.kiwicorp.dumbstrength.ui.chooseactivitycommon.ChooseActivityListAdapter
import com.kiwicorp.dumbstrength.ui.chooseactivitycommon.SearchActivityViewModel
import com.kiwicorp.dumbstrength.util.Mode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseActivityFragment : Fragment() {

    private lateinit var binding: FragmentChooseActivityBinding

    // must pass defaultViewModelProviderFactory https://github.com/google/dagger/issues/1935
    private val viewModel: AddEditRoutineViewModel by navGraphViewModels(R.id.addEditRoutineGraph) {
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
        setupAppBar()
    }

    private fun setupRecyclerView() {
        adapter = ChooseActivityListAdapter(viewModel)
        binding.activityRecyclerView.adapter = adapter
        searchViewModel.activities.observe(viewLifecycleOwner, Observer {
            adapter.addHeadersAndSubmitList(it)
        })
    }

    private fun setupAppBar() {
        setupSearchView()
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener if(it.itemId == R.id.menu_item_add_activity) {
                findNavController().navigate(toAddEditActivityFragment(Mode.ADD,null))
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