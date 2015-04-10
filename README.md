# Map Burrito
Mapping utilities and convenience wrapper for vector tile maps on Android

## Overview
Map Burrito is a tasty wrapper for [VTM](https://github.com/mapzen/vtm) that makes all your delicious vector tile map layers easier to handle and consume.

## Importing project into Android Studio
1. Clone https://github.com/mapzen/map-burrito.git
2. Open Android Studio and choose _File > Import project..._ and select project root folder
3. Follow instructions to enable [unit testing support](http://tools.android.com/tech-docs/unit-testing-support) in Android Studio
4. Modify unit test run configuration working directory to `/path/to/project/map-burrito/library`
5. Rebuild and run tests

## Running command line unit tests
```bash
$ ./gradlew clean assembleDebug test
```
