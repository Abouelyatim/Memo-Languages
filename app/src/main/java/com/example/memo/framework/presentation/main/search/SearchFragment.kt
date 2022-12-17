package com.example.memo.framework.presentation.main.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.memo.R
import com.example.memo.business.domain.models.Dictionary
import com.example.memo.business.domain.utils.StateMessageCallback
import com.example.memo.databinding.FragmentSearchBinding
import com.example.memo.framework.presentation.main.dictionary.DictionaryEvents
import com.example.memo.framework.presentation.main.dictionary.DictionaryViewModel
import com.example.memo.framework.presentation.util.processQueue

class SearchFragment : BaseSearchFragment() {

    private val viewModel: SearchViewModel by viewModels()
    private val viewModelDictionary: DictionaryViewModel by viewModels()
    private var arrayAdapter: ArrayAdapter<String>? = null

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDictionaries()
        subscribeObservers()
        searchWord()
    }

    private fun searchWord() {
        binding.buttonSearch.setOnClickListener {
            binding.inputSearchWord.text.toString().let { word ->
                viewModel.onTriggerEvent(SearchEvents.SearchWordEvent(word))
                viewModel.onTriggerEvent(SearchEvents.SaveWordDataStoreEvent(word))
            }
        }
    }

    private fun setDropDownMenu(dictionaries: List<Dictionary>){
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, dictionaries.map { it.name })
        binding.inputSearchDictionary.setAdapter(arrayAdapter)

        binding.inputSearchDictionary.setOnItemClickListener { adapterView, view, i, l ->
            dictionaries.forEach {
                if (it.name == arrayAdapter!!.getItem(i)){
                    viewModel.onTriggerEvent(SearchEvents.UpdateCurrentUrlEvent(
                        it.url
                    ))
                    return@forEach
                }
            }
        }
    }

    private fun getDictionaries() {
        viewModelDictionary.onTriggerEvent(DictionaryEvents.GetDictionariesEvent)
    }

    private fun subscribeObservers() {
        viewModelDictionary.baseState.observe(viewLifecycleOwner) { baseState ->
            viewModelDictionary.state.observe(viewLifecycleOwner) { state ->

                uiCommunicationListener.displayProgressBar(baseState.isLoading)

                processQueue(
                    context = context,
                    queue = baseState.queue,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModelDictionary.onTriggerEvent(DictionaryEvents.OnRemoveHeadFromQueue)
                        }
                    })

                setDropDownMenu(
                    state.dictionaryList
                )
            }
        }

        viewModel.baseState.observe(viewLifecycleOwner) { baseState ->

            uiCommunicationListener.displayProgressBar(baseState.isLoading)

            processQueue(
                context = context,
                queue = baseState.queue,
                stateMessageCallback = object : StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(SearchEvents.OnRemoveHeadFromQueue)
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        arrayAdapter = null
    }
}