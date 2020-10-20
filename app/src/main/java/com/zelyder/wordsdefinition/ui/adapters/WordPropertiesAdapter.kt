package com.zelyder.wordsdefinition.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.wordsdefinition.databinding.DefinitionItemBinding
import com.zelyder.wordsdefinition.databinding.InputFieldItemBinding
import com.zelyder.wordsdefinition.domain.models.Definition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_INPUT_FIELD = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class WordPropertiesAdapter(private val clickListener: InputFieldListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DefinitionDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addInputFieldAndSubmitList(list: List<Definition>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.InputField)
                else -> listOf(DataItem.InputField) + list.map { DataItem.DefinitionItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.InputField -> ITEM_VIEW_TYPE_INPUT_FIELD
            is DataItem.DefinitionItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_INPUT_FIELD -> InputFieldViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> DefinitionItemViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DefinitionItemViewHolder -> {
                val item = getItem(position) as DataItem.DefinitionItem
                holder.bind(item.definition)
            }
            is InputFieldViewHolder -> {
                holder.bind(clickListener)
            }
        }
    }

    class InputFieldViewHolder(private val binding: InputFieldItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: InputFieldListener) {
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InputFieldViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = InputFieldItemBinding.inflate(layoutInflater, parent, false)
                return InputFieldViewHolder(binding)
            }
        }
    }


    class DefinitionItemViewHolder(private val binding: DefinitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Definition) {
            binding.tvDefinition.text = item.definition
            binding.tvPartOfSpeech.text = item.partOfSpeech
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DefinitionItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DefinitionItemBinding.inflate(layoutInflater, parent, false)
                return DefinitionItemViewHolder(binding)
            }
        }

    }

    class DefinitionDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class DataItem {

    abstract val id: Long

    data class DefinitionItem(val definition: Definition) : DataItem() {
        override val id = definition.id
    }

    object InputField : DataItem() {
        override val id = Long.MIN_VALUE
    }
}

class InputFieldListener(val clickListener: (text: String) -> Unit) {
    fun onClick(word: String) = clickListener(word)
}
