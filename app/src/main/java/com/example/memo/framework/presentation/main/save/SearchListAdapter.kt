package com.example.memo.framework.presentation.main.save

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.memo.business.domain.models.Search
import com.example.memo.databinding.LayoutSearchListItemBinding

class SearchListAdapter (
    private val interaction: Interaction? = null
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Search>() {

        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }
    }
    private val differ =
        AsyncListDiffer(
            SearchRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(
            LayoutSearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction = interaction,
        )
    }

    internal inner class SearchRecyclerChangeCallback(
        private val adapter: SearchListAdapter
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
            is SearchViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(blogList: List<Search>?, ){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    class SearchViewHolder
    constructor(
        private val binding: LayoutSearchListItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Search) {
            binding.deleteSave.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.saveWord.text = item.word
            binding.saveLanguageSource.text = item.languageSource
            binding.saveLanguageDestination.text = item.languageDestination
            binding.saveUrl.text = item.url
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Search)
    }
}