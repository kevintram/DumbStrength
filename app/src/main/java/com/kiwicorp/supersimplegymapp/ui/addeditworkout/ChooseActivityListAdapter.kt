package com.kiwicorp.supersimplegymapp.ui.addeditworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.EntryWithActivity
import com.kiwicorp.supersimplegymapp.databinding.ItemCheckableActivityBinding

class ChooseActivityListAdapter(private val viewModel: AddEditWorkoutViewModel):
    ListAdapter<Activity,ChooseActivityListAdapter.CheckableActivityViewHolder>(ActivityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckableActivityViewHolder {
        return CheckableActivityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CheckableActivityViewHolder, position: Int) {
        holder.bind(getItem(position),viewModel)
    }

    class CheckableActivityViewHolder(private val binding: ItemCheckableActivityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity, viewModel: AddEditWorkoutViewModel) {
            binding.activity = activity

            binding.layout.setOnClickListener {
                binding.checkbox.isChecked = !binding.checkbox.isChecked
            }

            binding.checkbox.isChecked = activityIsInEntries(activity, viewModel.entries.value!!)

            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.chooseActivity(activity)
                } else {
                    viewModel.unchooseActivity(activity)
                }
            }

        }

        private fun activityIsInEntries(activity: Activity, entriesWithActivity: List<EntryWithActivity>): Boolean {
            for (entryWithActivity in entriesWithActivity) {
                if (entryWithActivity.activity == activity) {
                    return true
                }
            }
            return false
        }

        companion object {
            fun from(parent: ViewGroup): CheckableActivityViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCheckableActivityBinding.inflate(layoutInflater,parent,false)

                return CheckableActivityViewHolder(binding)
            }
        }
    }

    class ActivityDiffCallback : DiffUtil.ItemCallback<Activity>() {
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem == newItem
        }
    }

}