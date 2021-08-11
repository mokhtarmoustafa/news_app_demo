package com.tempo.newsfeed.di


import com.tempo.newsfeed.news.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeThemeFragment(): NewsFragment

}
