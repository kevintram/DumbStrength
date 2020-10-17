package com.kiwicorp.supersimplegymapp.ui.addeditworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.supersimplegymapp.data.WorkoutEntryWithActivity
import com.kiwicorp.supersimplegymapp.databinding.ItemEntryBinding

class EntryWithActivityListAdapter(private val viewModel: AddEditWorkoutViewModel):
    ListAdapter<WorkoutEntryWithActivity,EntryWithActivityListAdapter.EntryWithActivityViewHolder>(EntryWithActivityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryWithActivityViewHolder {
        return EntryWithActivityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EntryWithActivityViewHolder, position: Int) {
        holder.bind(getItem(position),viewModel)
    }

    class EntryWithActivityViewHolder(private val binding: ItemEntryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(workoutEntryWithActivity: WorkoutEntryWithActivity, viewModel: AddEditWorkoutViewModel) {
            binding.entryWithActivity = workoutEntryWithActivity
            binding.viewModel = viewModel
            binding.entryDescriptionText.doOnTextChanged { text, _, _, _ ->
                workoutEntryWithActivity.workoutEntry.description = text.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): EntryWithActivityViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEntryBinding.inflate(layoutInflater, parent, false)

                return EntryWithActivityViewHolder(binding)
            }
        }
    }

    class EntryWithActivityDiffCallback: DiffUtil.ItemCallback<WorkoutEntryWithActivity>() {
        override fun areItemsTheSame(
            oldItem: WorkoutEntryWithActivity,
            newItem: WorkoutEntryWithActivity
        ): Boolean {
            return oldItem.workoutEntry.id == newItem.workoutEntry.id
        }

        override fun areContentsTheSame(
            oldItem: WorkoutEntryWithActivity,
            newItem: WorkoutEntryWithActivity
        ): Boolean {
            return oldItem == newItem
        }

    }
}