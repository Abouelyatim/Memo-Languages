package com.example.memo.framework.presentation.main.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memo.business.domain.models.Search
import com.example.memo.business.domain.utils.StateMessageCallback
import com.example.memo.databinding.FragmentSearchListBinding
import com.example.memo.framework.presentation.util.processQueue

class SearchListFragment : BaseSaveFragment(),
    SearchListAdapter.Interaction{

    private var recyclerAdapter: SearchListAdapter? = null
    private val viewModel: SaveViewModel by viewModels()

    private var _binding: FragmentSearchListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeObservers()
        getSearches()
        goBack()
    }

    private fun goBack() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun deleteSearch(pk: Int){
        viewModel.onTriggerEvent(SaveEvents.DeleteSearchEvent(pk))
    }

    private fun getSearches() {
        viewModel.onTriggerEvent(SaveEvents.GetSearchesEvent)
    }

    private fun subscribeObservers() {
        viewModel.baseState.observe(viewLifecycleOwner) { baseState ->
            viewModel.state.observe(viewLifecycleOwner) { state ->

                uiCommunicationListener.displayProgressBar(baseState.isLoading)

                processQueue(
                    context = context,
                    queue = baseState.queue,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.onTriggerEvent(SaveEvents.OnRemoveHeadFromQueue)
                        }
                    })

                recyclerAdapter?.apply {
                    submitList(blogList = state.searchList)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerviewSave.apply {
            layoutManager = LinearLayoutManager(this@SearchListFragment.context)

            recyclerAdapter = SearchListAdapter(this@SearchListFragment)
            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Search) {
        item.pk?.let {
            deleteSearch(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerAdapter = null
        _binding = null
    }
}