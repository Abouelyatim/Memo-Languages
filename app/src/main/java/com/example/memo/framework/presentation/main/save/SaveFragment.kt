package com.example.memo.framework.presentation.main.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.memo.R
import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.StateMessageCallback
import com.example.memo.business.domain.utils.isNotEmptyAndNotBlank
import com.example.memo.databinding.FragmentSaveBinding
import com.example.memo.framework.presentation.main.language.LanguageEvents
import com.example.memo.framework.presentation.main.language.LanguageViewModel
import com.example.memo.framework.presentation.main.search.SearchEvents
import com.example.memo.framework.presentation.main.search.SearchViewModel
import com.example.memo.framework.presentation.util.processQueue

class SaveFragment : BaseSaveFragment() {

    private val viewModel: SaveViewModel by viewModels()
    private val viewModelLanguage: LanguageViewModel by viewModels()
    private val viewModelSearch: SearchViewModel by viewModels()
    private var arrayAdapterLanguageSource: ArrayAdapter<String>? = null
    private var arrayAdapterLanguageDestination: ArrayAdapter<String>? = null

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLanguages()
        subscribeObservers()
        saveSearch()
        getSavedWordAndUrl()
        allSearches()
    }

    private fun allSearches() {
        binding.buttonSearchAll.setOnClickListener {
            findNavController().navigate(R.id.action_saveFragment_to_searchListFragment)
        }
    }

    private fun getSavedWordAndUrl() {
        viewModel.onTriggerEvent(SaveEvents.ReadWordDataStoreEvent)
        viewModel.onTriggerEvent(SaveEvents.ReadUrlBundleEvent)
    }

    private fun saveSearch() {
        binding.buttonSave.setOnClickListener {
            viewModel.onTriggerEvent(
                SaveEvents.InsertSearchEvent(
                    binding.inputSaveWord.text.toString(),
                    binding.inputSaveUrl.text.toString()
                )
            )
        }
    }

    private fun getLanguages() {
        viewModelLanguage.onTriggerEvent(
            LanguageEvents.GetLanguagesEvent
        )
    }

    private fun setDropDownMenuLanguageSource(languages: List<Language>){
        arrayAdapterLanguageSource = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages.map { it.name })
        binding.inputSaveLanguageSource.setAdapter(arrayAdapterLanguageSource)

        binding.inputSaveLanguageSource.setOnItemClickListener { adapterView, view, i, l ->
            viewModel.onTriggerEvent(
                SaveEvents.UpdateLanguageSourceEvent(
                    arrayAdapterLanguageSource!!.getItem(i)!!
                )
            )
        }
    }

    private fun setDropDownMenuLanguageDestination(languages: List<Language>){
        arrayAdapterLanguageDestination = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages.map { it.name })
        binding.inputSaveLanguageDestination.setAdapter(arrayAdapterLanguageDestination)

        binding.inputSaveLanguageDestination.setOnItemClickListener { adapterView, view, i, l ->
            viewModel.onTriggerEvent(
                SaveEvents.UpdateLanguageDestinationEvent(
                    arrayAdapterLanguageDestination!!.getItem(i)!!
                )
            )
        }
    }

    private fun subscribeObservers() {
        viewModelLanguage.baseState.observe(viewLifecycleOwner) { baseState ->
            viewModelLanguage.state.observe(viewLifecycleOwner) { state ->

                uiCommunicationListener.displayProgressBar(baseState.isLoading)

                processQueue(
                    context = context,
                    queue = baseState.queue,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModelLanguage.onTriggerEvent(LanguageEvents.OnRemoveHeadFromQueue)
                        }
                    })
                state.languageList.apply {
                    setDropDownMenuLanguageSource(this)
                    setDropDownMenuLanguageDestination(this)
                }
            }
        }

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

                if(state.isSavingCompleted){
                    viewModel.onTriggerEvent(SaveEvents.OnSavingCompletedEvent(false))
                    razeBundleAndDataStore()
                    viewModel.onTriggerEvent(SaveEvents.UpdateWordEvent(""))
                    viewModel.onTriggerEvent(SaveEvents.UpdateUrlEvent(""))
                    resetForm(url = "",word = "", languageSource = true, languageDestination = true)

                    if(state.word.isNotEmpty()){
                        requireActivity().finish()
                    }
                }

                if(state.word.isNotEmptyAndNotBlank() && state.url.isNotEmptyAndNotBlank()){
                    resetForm(
                        url = state.url,
                        word = state.word,
                        languageSource = false,
                        languageDestination = false
                    )
                }
            }
        }
    }

    private fun razeBundleAndDataStore(){
        bundleOf("SEARCH_URL" to "")
        viewModelSearch.onTriggerEvent(SearchEvents.SaveWordDataStoreEvent(""))
    }

    private fun resetForm(
        url:String,
        word:String,
        languageSource:Boolean,
        languageDestination:Boolean
    ){
        binding.inputSaveUrl.setText(url)
        binding.inputSaveWord.setText(word)
        if(languageSource){
            binding.inputSaveLanguageSource.text = null
        }
        if (languageDestination){
            binding.inputSaveLanguageDestination.text = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        arrayAdapterLanguageSource = null
        arrayAdapterLanguageDestination = null
    }
}