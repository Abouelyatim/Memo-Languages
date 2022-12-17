package com.example.memo.business.interactors.search

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.search.ports.SearchWordUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchWord(
    val app: Application,
) : SearchWordUseCase {

    override fun searchWord(word: String, url: String): Flow<DataState<Response>> = flow {
        emit(DataState.data<Response>(
            data = null,
            response = null,
        ))

        if(word.isNotEmptyAndNotBlank() && url.isNotEmptyAndNotBlank()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url + word)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            ContextCompat.startActivity(app.applicationContext, intent, null)
        }else{
            throw Exception(ErrorHandling.ERROR_MUST_NOT_EMPTY_OR_BLANC)
        }
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}