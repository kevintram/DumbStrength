package com.kiwicorp.supersimplegymapp.ui.activitydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.supersimplegymapp.data.WorkoutEntry
import com.kiwicorp.supersimplegymapp.databinding.ItemActivityDetailEntryBinding

class EntryListAdapter : ListAdapter<WorkoutEntry, EntryListAdapter.EntryViewHolder>(EntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EntryViewHolder(private val binding: ItemActivityDetailEntryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(workoutEntry: WorkoutEntry) {
            binding.entry = workoutEntry
        }

        companion object {
            fun from(parent: ViewGroup): EntryViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemActivityDetailEntryBinding.inflate(inflater, parent,false)

                return EntryViewHolder(binding)
            }
        }
    }

    class EntryDiffCallback: DiffUtil.ItemCallback<WorkoutEntry>() {
        override fun areItemsTheSame(
            oldItem: WorkoutEntry,
            newItem: WorkoutEntry
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WorkoutEntry,
            newItem: WorkoutEntry
        ): Boolean {
            return oldItem == newItem
        }
    }

}