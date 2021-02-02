<h1 align="center">Favy</h1>

<p align="center">  
Favy is a small demo application based on modern Android application tech-stacks and MVVM architecture.<br>This project is for focusing especially on the Fave task of creating Movie app based on TMDB API.
</p>
</br>

<p align="center">
<img src="/misc/screenshotFavy.png"/>
</p>

## Download
Go to the [Releases](https://github.com/afarhan39/Favy/blob/master/misc/Favy-v1.0(1)-release.apk) to download the latest APK.

## Tech stack & Open-source libraries
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Koin](https://github.com/InsertKoinIO/koin) for dependency injection
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide)
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- Custom Views
  - [SpringView](https://github.com/liaoinstan/SpringView) - A simple way of implementing pull to refresh and load more functionalities.
  - [CustomTabs](https://developers.google.com/web/android/custom-tabs) - A simple way to implement a webView inside apps.

## Architecture
Pokedex is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## Open API
Favy using the [TMDB](https://developers.themoviedb.org/3/movies) for constructing RESTful API.<br>
TMDB provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Movies and also TV Seriea.

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/afarhan39/Favy/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/afarhan39)__ me for my next creations! 🤩

Huge credits to __[skydoves](https://github.com/skydoves)__ for his nice template of Readme!

# License
```xml
Designed and developed by 2020 afarhan39 (Amir Farhan)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
