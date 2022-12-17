package com.example.memo.framework.presentation.main.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memo.business.domain.models.Dictionary
import com.example.memo.business.domain.utils.StateMessageCallback
import com.example.memo.databinding.FragmentDictionaryBinding
import com.example.memo.framework.presentation.util.processQueue

class DictionaryFragment : BaseDictionaryFragment(),
    DictionaryListAdapter.Interaction{

    private var recyclerAdapter: DictionaryListAdapter? = null
    private val viewModel: DictionaryViewModel by viewModels()

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeObservers()
        getDictionaries()
        addDictionary()
    }

    private fun addDictionary() {
        binding.buttonAddDictionary.setOnClickListener {
            val name = binding.inputAddDictionaryName.text.toString()
            val url = binding.inputAddDictionaryUrl.text.toString()
            viewModel.onTriggerEvent(
                DictionaryEvents.InsertDictionaryEvent(
                name, url
            ))
            binding.inputAddDictionaryName.setText("")
            binding.inputAddDictionaryUrl.setText("")
        }
    }

    private fun deleteDictionary(pk: Int){
        viewModel.onTriggerEvent(DictionaryEvents.DeleteDictionaryEvent(pk))
    }

    private fun getDictionaries() {
        viewModel.onTriggerEvent(DictionaryEvents.GetDictionariesEvent)
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
                            viewModel.onTriggerEvent(DictionaryEvents.OnRemoveHeadFromQueue)
                        }
                    })

                recyclerAdapter?.apply {
                    submitList(blogList = state.dictionaryList)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerviewDictionaries.apply {
            layoutManager = LinearLayoutManager(this@DictionaryFragment.context)

            recyclerAdapter = DictionaryListAdapter(this@DictionaryFragment)
            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Dictionary) {
        item.pk?.let {
            deleteDictionary(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerAdapter = null
        _binding = null
    }
}