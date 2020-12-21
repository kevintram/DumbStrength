package com.kiwicorp.dumbstrength.ui.activitydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.dumbstrength.data.Activity
import com.kiwicorp.dumbstrength.data.WorkoutEntry
import com.kiwicorp.dumbstrength.databinding.ItemActivityDetailEntryBinding
import com.kiwicorp.dumbstrength.databinding.ItemActivityEntryHeaderBinding
import kotlinx.coroutines.*

const val ITEM_ENTRY_VIEW_TYPE = 0
const val ITEM_HEADER_VIEW_TYPE = 1

class EntryListAdapter: ListAdapter<EntryListAdapter.Item, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    private var submitListJob: Job? = null

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is Item.EntryItem -> ITEM_ENTRY_VIEW_TYPE
            is Item.HeaderItem -> ITEM_HEADER_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_ENTRY_VIEW_TYPE -> EntryViewHolder.from(parent)
            ITEM_HEADER_VIEW_TYPE -> HeaderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is EntryViewHolder -> {
                val entry = (getItem(position) as Item.EntryItem).entry
                holder.bind(entry)
            }
            is HeaderViewHolder -> {
                val activity = (getItem(position) as Item.HeaderItem).activity
                holder.bind(activity)
            }
        }
    }

    fun addHeaderAndSubmitList(entries: List<WorkoutEntry>?, activity: Activity?) {
        submitListJob?.cancel()

        submitListJob = adapterScope.launch {
            val items: List<Item> =  if (activity != null) {
                addHeader(entries ?: listOf(), activity)
            } else {
                listOf()
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    private fun addHeader(entries: List<WorkoutEntry>, activity: Activity): List<Item> {
        val items = mutableListOf<Item>(Item.HeaderItem(activity))

        for (entry in entries) {
            items.add(Item.EntryItem(entry))
        }

        return items
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

    class HeaderViewHolder(private val binding: ItemActivityEntryHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity) {
            binding.activity = activity
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemActivityEntryHeaderBinding.inflate(inflater, parent, false)

                return HeaderViewHolder(binding)
            }
        }
    }

    class ItemDiffCallback: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    sealed class Item {
        abstract val id: String

        data class EntryItem(val entry: WorkoutEntry): Item() {
            override val id: String = entry.id
        }

        data class HeaderItem(val activity: Activity): Item() {
            override val id: String = activity.id
        }
    }

}