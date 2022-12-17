package com.example.memo.framework.presentation

interface UICommunicationListener {

    fun displayProgressBar(isLoading: Boolean)

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean
}