package com.kiwicorp.supersimplegymapp.ui.addeditroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.supersimplegymapp.data.RoutineEntryWithActivity
import com.kiwicorp.supersimplegymapp.databinding.ItemRoutineEntryBinding

class RoutineEntryWithActivityListAdapter(private val viewModel: AddEditRoutineViewModel):
    ListAdapter<RoutineEntryWithActivity,RoutineEntryWithActivityListAdapter.RoutineEntryWithActivityViewHolder>(RoutineEntryWithActivityDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoutineEntryWithActivityViewHolder {
        return RoutineEntryWithActivityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RoutineEntryWithActivityViewHolder, position: Int) {
        holder.bind(getItem(position),viewModel)
    }


    class RoutineEntryWithActivityViewHolder(private val binding: ItemRoutineEntryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(routineEntryWithActivity: RoutineEntryWithActivity, viewModel: AddEditRoutineViewModel) {
            binding.entryWithActivity = routineEntryWithActivity
            binding.viewModel = viewModel
        }

        companion object {
            fun from(parent: ViewGroup): RoutineEntryWithActivityViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemRoutineEntryBinding.inflate(inflater, parent, false)

                return RoutineEntryWithActivityViewHolder(binding)
            }
        }
    }

    class RoutineEntryWithActivityDiffCallback : DiffUtil.ItemCallback<RoutineEntryWithActivity>() {
        override fun areItemsTheSame(
            oldItem: RoutineEntryWithActivity,
            newItem: RoutineEntryWithActivity
        ): Boolean {
            return oldItem.routineEntry.id == newItem.routineEntry.id
        }

        override fun areContentsTheSame(
            oldItem: RoutineEntryWithActivity,
            newItem: RoutineEntryWithActivity
        ): Boolean {
            return oldItem == newItem
        }

    }
}