package com.tempo.newsfeed.data.newsSet

import com.tempo.newsfeed.api.BaseDataSource
import com.tempo.newsfeed.api.NewsListResponse
import com.tempo.newsfeed.api.NewsService
import com.tempo.newsfeed.common.Default_Search
import com.tempo.newsfeed.data.Result
import javax.inject.Inject

/* Works with the News API to get data. */
class NewsRemoteDataSource @Inject constructor(private val service: NewsService) : BaseDataSource() {

    suspend fun fetchNewsList(apiKey : String, page : Int, pageSize : Int ) : Result<NewsListResponse> {
        return getResult { service.getEveryThingNewsList(apiKey, page,pageSize,Default_Search) }
    }
}
