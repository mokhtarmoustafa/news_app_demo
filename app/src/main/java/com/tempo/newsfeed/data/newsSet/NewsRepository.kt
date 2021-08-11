package com.tempo.newsfeed.data.newsSet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.tempo.newsfeed.api.Data
import com.tempo.newsfeed.api.NetworkState
import com.tempo.newsfeed.api.NewsListModel
import com.tempo.newsfeed.data.dao.NewsDao
import com.tempo.newsfeed.testing.OpenForTesting
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRemoteDataSource: NewsRemoteDataSource) {

    fun observePagedNews(connectivityAvailable : Boolean, coroutineScope: CoroutineScope)
            : Data<NewsListModel> {

        return if (connectivityAvailable)
            observeRemotePagedNews(coroutineScope)
        else observeLocalPagedNews()
    }
    private fun observeLocalPagedNews(): Data<NewsListModel> {

        val dataSourceFactory = newsDao.getPagedNews()

        val createLD = MutableLiveData<NetworkState>()
        createLD.postValue(NetworkState.LOADED)

        return Data(LivePagedListBuilder(dataSourceFactory,
            NewsPageDataSourceFactory.pagedListConfig()).build(),createLD)
    }

    private fun observeRemotePagedNews(ioCoroutineScope: CoroutineScope): Data<NewsListModel> {
        val dataSourceFactory = NewsPageDataSourceFactory(newsRemoteDataSource,
            newsDao, ioCoroutineScope)

        val networkState = Transformations.switchMap(dataSourceFactory.liveData) {
            it.networkState
        }
        return Data(LivePagedListBuilder(dataSourceFactory,
            NewsPageDataSourceFactory.pagedListConfig()).build(),networkState)
    }
}
