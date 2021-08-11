package com.tempo.newsfeed.news

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tempo.newsfeed.api.Data
import com.tempo.newsfeed.api.NewsListModel
import com.tempo.newsfeed.common.Default_Search
import com.tempo.newsfeed.data.newsSet.NewsRepository
import com.tempo.newsfeed.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {
    private var newsList: Data<NewsListModel>? = null

    fun newsList(connectivityAvailable: Boolean): Data<NewsListModel>? {
        newsList = repository.observePagedNews(connectivityAvailable, ioCoroutineScope)
        return newsList
    }

    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
