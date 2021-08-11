package com.tempo.newsfeed.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tempo.newsfeed.api.NewsService
import com.tempo.newsfeed.data.AppDatabase
import com.tempo.newsfeed.data.dao.NewsDao
import com.tempo.newsfeed.data.newsSet.NewsRemoteDataSource
import com.tempo.newsfeed.data.newsSet.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class NewsRepositoryTest {

    private lateinit var repository: NewsRepository
    private val dao = mock(NewsDao::class.java)
    private val service = mock(NewsService::class.java)
    private val remoteDataSource = NewsRemoteDataSource(service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.getNewsListDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = NewsRepository(dao, remoteDataSource)
    }

    @Test
    fun loadNewsFromNetwork() {
        runBlocking {
            repository.observePagedNews(connectivityAvailable = true,
                     coroutineScope = coroutineScope)

            verify(dao, never()).getPagedNews()
            verifyZeroInteractions(dao)
        }
    }
}
