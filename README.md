<h1 align="center">Lictionary</h1>

<div align="center">
  <strong>Native Android App</strong>
</div>
<div align="center">
  The app was built using Jetpack Compose, Kotlin and other related tools to provide a robust and efficient user experience.
</div>
<br />
<div align="center">
 <img src = "https://github.com/moemaair/Lictionary/blob/main/lictionary_assets/lictionary_logo.png" width = "300px"/>
</div>

<br />

<div align="center">

<a href="https://github.com/moemaair/Lictionary/blob/main/lictionary_assets/debug/app_debug_v1.apk" >
      <img src = "https://img.shields.io/badge/Master-Master?color=7885FF&label=Sample%20App&logo=android&style=for-the-badge" />
    </a> 
    <a href = "https://developer.android.com/jetpack/androidx/versions/all-channel#december_16_2020">
      <img src = "https://img.shields.io/badge/Jetpack%20Compose-1.1.1-brightgreen" />
    </a>
    <a href = "https://github.com/moemaair/Lictionary/blob/main/LICENSE"> 
     <img src = "https://img.shields.io/github/license/moemaair/lictionary" />
  </a>
  <a href = "https://twitter.com/codingin254">
     <img src = "https://img.shields.io/twitter/url?style=social&url=https%3A%2F%2Ftwitter.com%2Fcodingin254" />
  </a>
</div>

## Lictionary
Lictionary is a mobile dictionary application for Android devices. It allows users to quickly and easily look up words and their meanings, as well as see related information such as synonyms, antonyms and example sentences. The app features a simple and user-friendly interface, making it easy for users to find the information they need.

## Screens
<h3 align="center">Home</h3>

| Light mode                                                                                                 | Dark mode                                                                                                 |
|------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| ![](https://github.com/moemaair/Lictionary/blob/main/lictionary_assets/lictionary_light_home.png =300x300) | ![](https://github.com/moemaair/Lictionary/blob/main/lictionary_assets/lictionary_dark_home.png =300x300) |

<h3 align="center">Navigation drawer</h3>

| Light mode                                                                                                   | Dark mode                                                                                                   |
|--------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| ![](https://github.com/moemaair/Lictionary/blob/main/lictionary_assets/lictionary_light_drawer.png =300x300) | ![](https://github.com/moemaair/Lictionary/blob/main/lictionary_assets/lictionary_dark_drawer.png =300x300) |

## Built With 🛠

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - handle the stream of data asynchronously that executes sequentially.
    * [Hilt - Dagger ](https://dagger.dev/hilt/) - a pragmatic lightweight dependency injection framework.
    * [Jetpack](https://developer.android.com/jetpack)
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library provides an abstraction layer over SQLite.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.

## Architecture used

This app uses [***Android Clean Architecture (MVVM)***](https://developer.android.com/topic/architecture) architecture.

├── Core
├── Data
├── Domain
└── Presentation

![](clean_arch_pic.png =100x100)

## Contact

Drop a mail to:- ibrahimohamed81@outlook.com

## Credits

- Fonts are from [Google fonts](https://fonts.google.com/knowledge)
- Api - [Free Dictionary API](https://dictionaryapi.dev/)

## License

```
MIT License

Copyright (c) 2023 Mohamed Ibrahim

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```