package com.example.memo.framework.presentation.main.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.memo.business.domain.models.Dictionary
import com.example.memo.databinding.LayoutDictionaryListItemBinding

class DictionaryListAdapter (
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Dictionary>() {

        override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem == newItem
        }
    }
    private val differ =
        AsyncListDiffer(
            DictionaryRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DictionaryViewHolder(
            LayoutDictionaryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction = interaction,
        )
    }

    internal inner class DictionaryRecyclerChangeCallback(
        private val adapter: DictionaryListAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DictionaryViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(blogList: List<Dictionary>?, ){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    class DictionaryViewHolder
    constructor(
        private val binding: LayoutDictionaryListItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Dictionary) {
            binding.deleteDictionary.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.dictionaryName.text = item.name
            binding.dictionaryUrl.text = item.url
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Dictionary)
    }
}