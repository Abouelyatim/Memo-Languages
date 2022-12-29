package com.example.memo.framework.presentation.main

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationService : Service() {

    companion object {
        const val DISMISS_ACTION = "com.example.memo.framework.presentation.main.NotificationService.DISMISS_ACTION"
        const val VISIT_URL_ACTION = "com.example.memo.framework.presentation.main.NotificationService.VISIT_URL_ACTION"
        const val NOTIFICATION_CHANNEL_ID = "channelId"

        const val NOTIFICATION_PK = "com.example.memo.framework.presentation.main.NotificationService.NOTIFICATION_PK"
        const val NOTIFICATION_PK_SEARCH = "com.example.memo.framework.presentation.main.NotificationService.NOTIFICATION_PK_SEARCH"
        const val NOTIFICATION_OLD_DISMISS = "com.example.memo.framework.presentation.main.NotificationService.NOTIFICATION_OLD_DISMISS"
        const val NOTIFICATION_WORD = "com.example.memo.framework.presentation.main.NotificationService.NOTIFICATION_WORD"
        const val NOTIFICATION_URL = "com.example.memo.framework.presentation.main.NotificationService.NOTIFICATION_URL"

        var notificationServiceListener: NotificationServiceListener? = null
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @DelicateCoroutinesApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            DISMISS_ACTION ->{
                GlobalScope.launch(Dispatchers.Main) {
                    handleDismissAction(
                        intent.getIntExtra(NOTIFICATION_PK,0),
                        intent.getIntExtra(NOTIFICATION_PK_SEARCH,0),
                        intent.getIntExtra(NOTIFICATION_OLD_DISMISS,0)
                    )
                }
            }

            VISIT_URL_ACTION ->{
                GlobalScope.launch(Dispatchers.Main) {
                    handleVisitUrlAction(
                        intent.getIntExtra(NOTIFICATION_PK,0),
                        intent.getStringExtra(NOTIFICATION_WORD)!!,
                        intent.getStringExtra(NOTIFICATION_URL)!!
                    )
                }
            }
        }
        return START_NOT_STICKY;
    }

    private fun handleVisitUrlAction(pk: Int,word:String,url:String) {
        if (notificationServiceListener != null) {
            notificationServiceListener!!.onVisitUrlAction(pk, word, url)
        }
    }

    private fun handleDismissAction(pk:Int,pkSearch:Int,oldDismiss:Int) {
        if (notificationServiceListener != null) {
            notificationServiceListener!!.onDismissAction(pk,pkSearch,oldDismiss)
        }
    }

    interface NotificationServiceListener {
        fun onVisitUrlAction(pk: Int,word:String,url:String)
        fun onDismissAction(pk:Int,pkSearch:Int,oldDismiss:Int)
    }
}