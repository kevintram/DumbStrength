package com.kiwicorp.supersimplegymapp.ui.addeditworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.supersimplegymapp.data.EntryWithActivity
import com.kiwicorp.supersimplegymapp.databinding.ItemEntryBinding

class EntryListAdapter(private val viewModel: AddEditWorkoutViewModel):
    ListAdapter<EntryWithActivity,EntryListAdapter.EntryViewHolder>(EntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(getItem(position),viewModel)
    }

    class EntryViewHolder(private val binding: ItemEntryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(entryWithActivity: EntryWithActivity, viewModel: AddEditWorkoutViewModel) {
            binding.entryWithActivity = entryWithActivity
            binding.viewModel = viewModel
            binding.entryDescriptionText.doOnTextChanged { text, _, _, _ ->
                entryWithActivity.entry.description = text.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): EntryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEntryBinding.inflate(layoutInflater, parent, false)

                return EntryViewHolder(binding)
            }
        }
    }

    class EntryDiffCallback: DiffUtil.ItemCallback<EntryWithActivity>() {
        override fun areItemsTheSame(
            oldItem: EntryWithActivity,
            newItem: EntryWithActivity
        ): Boolean {
            return oldItem.entry.id == newItem.entry.id
        }

        override fun areContentsTheSame(
            oldItem: EntryWithActivity,
            newItem: EntryWithActivity
        ): Boolean {
            return oldItem == newItem
        }

    }
}