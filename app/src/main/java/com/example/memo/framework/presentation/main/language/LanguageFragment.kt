package com.example.memo.framework.presentation.main.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.StateMessageCallback
import com.example.memo.databinding.FragmentLanguageBinding
import com.example.memo.framework.presentation.util.processQueue

class LanguageFragment : BaseLanguageFragment(),
    LanguageListAdapter.Interaction {

    private var recyclerAdapter: LanguageListAdapter? = null
    private val viewModel: LanguageViewModel by viewModels()

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeObservers()
        getLanguages()
        addLanguage()
    }

    private fun addLanguage() {
        binding.buttonAddLanguage.setOnClickListener {
            val language = binding.inputAddLanguage.text.toString()
            viewModel.onTriggerEvent(LanguageEvents.InsertLanguageEvent(
                language
            ))
            binding.inputAddLanguage.setText("")
        }
    }

    private fun deleteLanguage(pk: Int){
        viewModel.onTriggerEvent(LanguageEvents.DeleteLanguageEvent(pk))
    }

    private fun getLanguages() {
        viewModel.onTriggerEvent(LanguageEvents.GetLanguagesEvent)
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
                            viewModel.onTriggerEvent(LanguageEvents.OnRemoveHeadFromQueue)
                        }
                    })

                recyclerAdapter?.apply {
                    submitList(blogList = state.languageList)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerviewLanguages.apply {
            layoutManager = LinearLayoutManager(this@LanguageFragment.context)

            recyclerAdapter = LanguageListAdapter(this@LanguageFragment)
            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Language) {
        item.pk?.let {
            deleteLanguage(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerAdapter = null
        _binding = null
    }
}