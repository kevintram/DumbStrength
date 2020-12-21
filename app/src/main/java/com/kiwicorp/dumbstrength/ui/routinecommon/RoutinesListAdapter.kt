package com.kiwicorp.dumbstrength.ui.routinecommon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.dumbstrength.data.Routine
import com.kiwicorp.dumbstrength.data.RoutineWithEntries
import com.kiwicorp.dumbstrength.databinding.ItemRoutineBinding

class RoutinesListAdapter(private val onRoutineClickListener: OnRoutineClickListener):
    ListAdapter<RoutineWithEntries, RoutinesListAdapter.RoutinesEntriesViewHolder>(RoutinesWithEntriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutinesEntriesViewHolder {
        return RoutinesEntriesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RoutinesEntriesViewHolder, position: Int) {
        holder.bind(getItem(position), onRoutineClickListener)
    }

    class RoutinesEntriesViewHolder(private val binding: ItemRoutineBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(routineWithEntries: RoutineWithEntries, onRoutineClickListener: OnRoutineClickListener) {
            binding.routineWithEntries = routineWithEntries
            binding.layout.setOnClickListener {
                onRoutineClickListener.onRoutineClick(routineWithEntries.routine)
            }
        }

        companion object {
            fun from(parent: ViewGroup): RoutinesEntriesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemRoutineBinding.inflate(inflater, parent, false)

                return RoutinesEntriesViewHolder(binding)
            }
        }
    }

    class RoutinesWithEntriesDiffCallback : DiffUtil.ItemCallback<RoutineWithEntries>() {
        override fun areItemsTheSame(
            oldItem: RoutineWithEntries,
            newItem: RoutineWithEntries
        ): Boolean {
            return oldItem.routine.id == newItem.routine.id
        }

        override fun areContentsTheSame(
            oldItem: RoutineWithEntries,
            newItem: RoutineWithEntries
        ): Boolean {
            return oldItem == newItem
        }

    }

    interface OnRoutineClickListener {
        fun onRoutineClick(routine: Routine)
    }
}