package com.zelyder.wordsdefinition.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zelyder.wordsdefinition.api.WordsApi
import com.zelyder.wordsdefinition.domain.models.Definition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class WordOverviewViewModel : ViewModel() {

    private val _definitions = MutableLiveData<List<Definition>>()

    val definitions: LiveData<List<Definition>>
        get() = _definitions


    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getWordDefinitions(word: String) {
        coroutineScope.launch {
            val getPropertiesDeferred = WordsApi.retrofitService.getDefinitions(word)
            try {
                val listResult = getPropertiesDeferred.await()
                _definitions.value = listResult.definitions
            } catch (t: Throwable) {
                Log.d(this.javaClass.name, "Failure: " + t.message)
            }

        }
    }

    init {
        getWordDefinitions("cat")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}