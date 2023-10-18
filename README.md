# Traction

*Ensure you have the latest version of Android studio (Android Studio Flamingo | 2022.2.1 preferrably) before cloning this repository.*

This is a simple android application that displays a list of movies.
In this app, at the top of the screen, there is a text field that can be used to search for movies from the local database.

![](https://github.com/Jeff-Emuveyan/Memo/blob/master/marsMarsterpiece8738100.gif?raw=true)

## Dependencies
1) Room
2) RxAndroid/RxJava
3) Jetpack Pagination library
4) Jetpack Compose

## How it works

The UI of this app was created using [Jetpack Compose](https://developer.android.com/jetpack/compose/tutorial). 
I also used [Jetpack Pagination 3 library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) (and RxJava)
to get the list of movies from https://www.themoviedb.org/ , save them to the local room database and display them in the UI in a paginated manner.

## Architecture
While developing this app, I used the Clean Architectural pattern as a guideline to organize the code base into three distint layers: 
Presentation layer, Domain layer and Data layer.

## Modules
The app contains three main modules:
1) ```features```: This module serves as the presentation layer of the app. It contains the UI related logic.
2) ```domain```: The domain module is a bridge between the data module and the features module. It contains all usecases needed for the app to function.
3) ```data```: The data module serves as the data layer of the app. It contains repositories and other related class needed to get and save data.

## Tests
The code base contains unit tests and UI tests. These can be found here:

Database Instrumented unit test: https://github.com/Jeff-Emuveyan/traction/blob/master/core/database/src/androidTest/java/com/example/database/LocalSourceInstrumentedTest.kt

ViewModel unit test: https://github.com/Jeff-Emuveyan/traction/blob/master/features/movies/src/test/java/com/example/movies/MoviesScreenUnitTest.kt

### Automation Test 
I added an automated UI test using [UiAutomator](https://developer.android.com/training/testing/other-components/ui-automator) to launch and verify that the
the expected Composables are visbile when the app is launched.
This automated test can be found here: https://github.com/Jeff-Emuveyan/traction/blob/master/features/movies/src/androidTest/java/com/example/movies/MoviesScreenUiAutomationTest.kt

## Something extra! 😁😁😁
It is very important to monitor the performance of an android app so that issues such as slow startup time and UI jank can be spotted early and resolved.

Because of this, I wrote a [macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview) test to monitor the startup time
of the app.

This test can be found here:
https://github.com/Jeff-Emuveyan/traction/blob/master/benchmark/src/main/java/com/example/benchmark/TractionStartupBenchmark.kt

## CI/CD
Yes, the app has CI/CD setup. I used [CircleCi](https://circleci.com/) for this. 
The build config.yml file can be found here: https://github.com/Jeff-Emuveyan/traction/blob/master/.circleci/config.yml

When a new code is pushed to the repo, my CircleCi setup will run *unit tests* and then *build* the apk file.
