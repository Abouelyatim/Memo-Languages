package com.example.memo.framework.presentation.main.save

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.memo.R
import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.StateMessageCallback
import com.example.memo.business.domain.utils.isNotEmptyAndNotBlank
import com.example.memo.databinding.FragmentSaveBinding
import com.example.memo.framework.presentation.main.NotificationService
import com.example.memo.framework.presentation.main.language.LanguageEvents
import com.example.memo.framework.presentation.main.language.LanguageViewModel
import com.example.memo.framework.presentation.main.search.SearchEvents
import com.example.memo.framework.presentation.main.search.SearchViewModel
import com.example.memo.framework.presentation.util.processQueue
import java.text.SimpleDateFormat
import java.util.*


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
        handleNotificationActions()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLanguages()
        subscribeObservers()
        saveSearch()
        getSavedWordAndUrl()
        allSearches()
        setNotification()
        getNotifications()
    }

    private fun handleNotificationActions() {
        NotificationService.notificationServiceListener = object : NotificationService.NotificationServiceListener {
            override fun onVisitUrlAction(pk: Int,word:String,url:String) {
                viewModel.onTriggerEvent(SaveEvents.DeleteNotificationEvent(pk))
                NotificationManagerCompat.from(requireContext()).cancel(pk)
                //viewModel.onTriggerEvent(SaveEvents.OpenUrlOnBrowserEvent(word, url)
            }

            override fun onDismissAction(pk:Int,pkSearch: Int, oldDismiss: Int) {
                viewModel.onTriggerEvent(SaveEvents.DeleteNotificationEvent(pk))
                NotificationManagerCompat.from(requireContext()).cancel(pk)
                viewModel.onTriggerEvent(SaveEvents.UpdateSearchDismissEvent(pkSearch, oldDismiss))
            }
        }
    }

    private fun getNotifications() {
        viewModel.onTriggerEvent(SaveEvents.GetNotificationsEvent)
    }

    private fun setNotification() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            binding.buttonSearchNotification.setOnClickListener {
                state.notifications.forEach {
                    showNotification(it.pk!!,it.pkSearch,it.dismiss,it.word,it.url,"Rafraîchissez votre mémoire avec le mot ${it.word}")
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showNotification(notificationId:Int,pkSearch:Int,oldDismiss:Int,title:String,url:String,text:String) {
        createNotificationChannel()

        val date = Date()

        val intentDismiss = Intent(requireContext(),NotificationService::class.java)
            .setAction(NotificationService.DISMISS_ACTION)
            .putExtra(NotificationService.NOTIFICATION_PK_SEARCH,pkSearch)
            .putExtra(NotificationService.NOTIFICATION_OLD_DISMISS,oldDismiss)
            .putExtra(NotificationService.NOTIFICATION_PK,notificationId)
        val pendingIntentDismiss = PendingIntent.getService(
            requireContext(),
            SimpleDateFormat("mmssSSS").format(date).toInt(),
            intentDismiss,
            0)

        val intentVisitUrl = Intent(requireContext(),NotificationService::class.java)
            .setAction(NotificationService.VISIT_URL_ACTION)
            .putExtra(NotificationService.NOTIFICATION_PK,notificationId)
            .putExtra(NotificationService.NOTIFICATION_WORD,title)
            .putExtra(NotificationService.NOTIFICATION_URL,url)
        val pendingIntentVisitUrl = PendingIntent.getService(
            requireContext(),
            SimpleDateFormat("mmssSSS").format(date).toInt(),
            intentVisitUrl,
            0)


        val notificationBuilder = NotificationCompat.Builder(requireContext(),NotificationService.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_notification,"Ecarter",pendingIntentDismiss)
            .addAction(R.drawable.ic_notification,"Visiter site web",pendingIntentVisitUrl)

        val notificationManagerCompat = NotificationManagerCompat.from(requireContext())
        notificationManagerCompat.notify(notificationId,notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name  = "name"
            val description = "description"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel("channelId",name,importance)
            notificationChannel.description = description
            val notificationManager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
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