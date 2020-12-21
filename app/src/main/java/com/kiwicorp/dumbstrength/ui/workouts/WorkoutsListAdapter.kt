package com.kiwicorp.dumbstrength.ui.workouts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.dumbstrength.data.WorkoutWithEntries
import com.kiwicorp.dumbstrength.databinding.ItemWorkoutBinding

class WorkoutsListAdapter(private val viewModel: WorkoutsViewModel):
    ListAdapter<WorkoutWithEntries,WorkoutsListAdapter.WorkoutWithWorkoutComponentsViewHolder>(WorkoutWithWorkoutComponentsDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutWithWorkoutComponentsViewHolder {
        return WorkoutWithWorkoutComponentsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WorkoutWithWorkoutComponentsViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    class WorkoutWithWorkoutComponentsViewHolder(private val binding: ItemWorkoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(workoutWithEntries: WorkoutWithEntries, viewModel: WorkoutsViewModel) {
            binding.workoutWithEntries = workoutWithEntries
            binding.viewModel = viewModel
        }

        companion object {
            fun from(parent: ViewGroup): WorkoutWithWorkoutComponentsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWorkoutBinding.inflate(layoutInflater, parent, false)

                return WorkoutWithWorkoutComponentsViewHolder(binding)
            }
        }
    }

    class WorkoutWithWorkoutComponentsDiffCallback: DiffUtil.ItemCallback<WorkoutWithEntries>() {
        override fun areItemsTheSame(
            oldItem: WorkoutWithEntries,
            newItem: WorkoutWithEntries
        ): Boolean {
            return oldItem.workout.id == newItem.workout.id
        }

        override fun areContentsTheSame(
            oldItem: WorkoutWithEntries,
            newItem: WorkoutWithEntries
        ): Boolean {
            return oldItem == newItem
        }

    }
}