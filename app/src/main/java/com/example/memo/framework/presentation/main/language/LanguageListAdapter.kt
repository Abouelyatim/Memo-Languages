package com.example.memo.framework.presentation.main.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.memo.business.domain.models.Language
import com.example.memo.databinding.LayoutLanguageListItemBinding

class LanguageListAdapter (
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Language>() {

        override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
            return oldItem == newItem
        }
    }
    private val differ =
        AsyncListDiffer(
            LanguageRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LanguageViewHolder(
            LayoutLanguageListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction = interaction,
        )
    }

    internal inner class LanguageRecyclerChangeCallback(
        private val adapter: LanguageListAdapter
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
            is LanguageViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(blogList: List<Language>?, ){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    class LanguageViewHolder
    constructor(
        private val binding: LayoutLanguageListItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Language) {
            binding.deleteOptionValue.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.languageName.text = item.name
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Language)
    }
}