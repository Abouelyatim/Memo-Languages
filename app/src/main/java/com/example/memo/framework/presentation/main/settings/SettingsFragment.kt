package com.example.memo.framework.presentation.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.memo.R
import com.example.memo.business.domain.utils.StateMessageCallback
import com.example.memo.databinding.FragmentSettingsBinding
import com.example.memo.framework.presentation.main.search.SearchEvents
import com.example.memo.framework.presentation.util.processQueue

class SettingsFragment : BaseSettingsFragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private var arrayAdapterNotificationNumber: ArrayAdapter<String>? = null
    private var arrayAdapterDismissNumber: ArrayAdapter<String>? = null

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        getSettings()
    }

    override fun onStart() {
        super.onStart()
        setDropDownMenu()
    }

    private fun getSettings() {
        viewModel.onTriggerEvent(SettingsEvents.ReadSettingsDataStoreEvent)
    }

    private fun subscribeObservers() {
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

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.inputSettingsNotificationNumber.setText(state.notificationNumber.toString(),false)
            binding.inputSettingsDismissNumbre.setText(state.dismissNumber.toString(),false)
        }
    }

    private fun setDropDownMenu(){
        arrayAdapterNotificationNumber = ArrayAdapter(requireContext(), R.layout.dropdown_item, (1..10).toList().map { it.toString() })
        arrayAdapterDismissNumber = ArrayAdapter(requireContext(), R.layout.dropdown_item, (1..5).toList().map { it.toString() })
        binding.inputSettingsNotificationNumber.setAdapter(arrayAdapterNotificationNumber)
        binding.inputSettingsDismissNumbre.setAdapter(arrayAdapterDismissNumber)

        binding.inputSettingsNotificationNumber.setOnItemClickListener { adapterView, view, i, l ->
            viewModel.onTriggerEvent(SettingsEvents.UpdateNotificationNumberEvent(arrayAdapterNotificationNumber!!.getItem(i)!!.toInt()))
        }

        binding.inputSettingsDismissNumbre.setOnItemClickListener { adapterView, view, i, l ->
            viewModel.onTriggerEvent(SettingsEvents.UpdateDismissNumberEvent(arrayAdapterDismissNumber!!.getItem(i)!!.toInt()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        arrayAdapterNotificationNumber = null
        arrayAdapterDismissNumber = null
    }
}