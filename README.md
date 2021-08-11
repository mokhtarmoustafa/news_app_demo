# News-Feed Jetpack Components
App demonstrating Clean Architecture using Coroutines and Android Jetpack Components (Room, MVVM Paging, Navigation Components and Live Data)

# ScreenShots

<img src = "https://github.com/mokhtarmoustafa/news_app_demo/blob/master/screenshots/news_home.jpg" width = 260 height = 550/>
<img src = "https://github.com/mokhtarmoustafa/news_app_demo/blob/master/screenshots/news_detail.jpg" width = 260 height = 550/>
<img src = "https://github.com/mokhtarmoustafa/news_app_demo/blob/master/screenshots/news_web_view.jpg" width = 260 height = 550/>

# Tech-Stack

* __Retrofit__ : For Network calls
* __Architecture__ : MVVM
* __Coroutines__ for background operations like fetching network response
* __Room database__ : For offline persistence and Paging Library
* __Live Data__ : To notify view for change
* __Dagger__ : For dependency injection
* __Language__ : Kotlin

# Architecture Diagram
This application strictly follows the below architecture 

<img src = "https://github.com/mokhtarmoustafa/news_app_demo/blob/master/screenshots/Architecture.png" width = 450 />

# API Key
Get your api key from here https://newsapi.org/

Follow instructions in build-gradle (app) to integrate API key to build the project.
