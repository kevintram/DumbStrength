package com.kiwicorp.supersimplegymapp.ui.chooseactivitycommon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.databinding.ItemCheckableActivityBinding
import com.kiwicorp.supersimplegymapp.databinding.ItemLetterHeaderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ITEM_ACTIVITY_VIEW_TYPE = 0
const val ITEM_LETTER_HEADER_VIEW_TYPE = 1

class BetterChooseActivityListAdapter(private val chooseActivityActions: ChooseActivityActions):
    ListAdapter<BetterChooseActivityListAdapter.Item, RecyclerView.ViewHolder>(ItemDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_ACTIVITY_VIEW_TYPE -> CheckableActivityViewHolder.from(parent)
            ITEM_LETTER_HEADER_VIEW_TYPE -> LetterHeaderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CheckableActivityViewHolder -> {
                val activity = (getItem(position) as Item.ActivityItem).activity
                holder.bind(activity,chooseActivityActions)
            }
            is LetterHeaderViewHolder -> {
                val letter = (getItem(position) as Item.LetterHeaderItem).letter
                holder.bind(letter)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is Item.ActivityItem -> ITEM_ACTIVITY_VIEW_TYPE
            is Item.LetterHeaderItem -> ITEM_LETTER_HEADER_VIEW_TYPE
        }
    }

    fun addHeadersAndSubmitList(activities: List<Activity>?) {
        adapterScope.launch {
            val items = when(activities) {
                null -> listOf()
                else -> addHeaders(activities)
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    private fun addHeaders(activities: List<Activity>): List<Item> {
        if (activities.isEmpty()) return listOf()

        val items = mutableListOf<Item>()

        var currLetter = if (activities[0].name[0].isDigit()) '1' else activities[0].name[0]

        items.add(Item.LetterHeaderItem(currLetter))

        for (activity in activities) {
            val activityLetter = activity.name[0]
            if (activityLetter != currLetter) {
                if (!(activityLetter.isDigit() && currLetter.isDigit())) { // don't add new header if both are digits
                    currLetter = activityLetter
                    items.add(Item.LetterHeaderItem(currLetter))
                }
            }
            items.add(Item.ActivityItem(activity))
        }

        return items
    }

    class CheckableActivityViewHolder(private val binding: ItemCheckableActivityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity, chooseActivityActions: ChooseActivityActions) {
            binding.activity = activity

            binding.layout.setOnClickListener {
                binding.checkbox.isChecked = !binding.checkbox.isChecked
            }

            binding.checkbox.isChecked = chooseActivityActions.activityIsInEntries(activity)

            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    chooseActivityActions.chooseActivity(activity)
                } else {
                    chooseActivityActions.unchooseActivity(activity)
                }
            }

        }

        companion object {
            fun from(parent: ViewGroup): CheckableActivityViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCheckableActivityBinding.inflate(layoutInflater,parent,false)

                return CheckableActivityViewHolder(binding)
            }
        }
    }

    class LetterHeaderViewHolder(private val binding: ItemLetterHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(letter: Char) {
            binding.letterText.text = letter.toString()
        }

        companion object {
            fun from(parent: ViewGroup): LetterHeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemLetterHeaderBinding.inflate(inflater, parent, false)

                return LetterHeaderViewHolder(binding)
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    sealed class Item {
        abstract val id: String

        data class ActivityItem(val activity: Activity): Item() {
            override val id: String = activity.id
        }

        data class LetterHeaderItem(val letter: Char): Item() {
            override val id: String = letter.toString()
        }
    }

}