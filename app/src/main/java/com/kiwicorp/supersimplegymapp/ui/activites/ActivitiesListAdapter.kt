package com.kiwicorp.supersimplegymapp.ui.activites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.databinding.ItemActivityBinding

class ActivitiesListAdapter(private val viewModel: ActivitiesViewModel):
    ListAdapter<Activity,ActivitiesListAdapter.ActivityViewHolder>(ActivityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }


    class ActivityViewHolder(private val binding: ItemActivityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity, viewModel: ActivitiesViewModel) {
            binding.activity = activity
            binding.viewModel = viewModel
        }

        companion object {
            fun from(parent: ViewGroup): ActivityViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemActivityBinding.inflate(layoutInflater, parent, false)

                return ActivityViewHolder(binding)
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