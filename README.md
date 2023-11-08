# Currency Converter
A smartphone app to show top headlines for a specific source (BBC news)


## Instruction to use

```
Please choose app flavour from the build build variant. News headlines depend upon the flavour user chooses
```


## Apk link
(https://i.diawi.com/YLp1ZZ)



## Requirements

```
Android Studio Flamingo | 2022.2.1 Patch 1
Kotlin :222-1.8.20-release-AS3739.54
Minimum android version : API LEVEL 26 (Android v8.0 Oreo)
JavaVersion.VERSION_17
Gradle version 8.0 
```

## Dependencies & Technologies

```
- Hilt dependency injection
- Network : Coroutines, retrofit, moshi, gson and okhttp
- Kotlin
- Coroutines
- Kotlins Flows
- Paging 3
- Lifecycle
- Navigation Component
- Sdp & ssp library for responsive dimensions - it will generate its own dimen file so we don"t have to maintain ours manually
- Kotlin coil for image loading, downloading and cache
- Testing: Mockk, Mockito, Paging-testing, Coutines testing, Hilt
```

### The app has following packages:

```
di: It contains all the dependency injection related classes and interfaces.
presentation: View classes along with their corresponding ViewModel, adapters and models
utils: Utility classes.
data: All data related things like User Repository, remote APIs etc.
```


| Features      | Status |
| ----------- | ----------- |
| Get the input of a EUR amount      | DONE       |
| Connect to a backend to get exchange rates   | DONE        |
| Show the available rates for the entered EUR amount   | DONE        |
| Show a chart to plot the rate of the currency EUR against a target currency over a time periods   | DONE        |
| Allow the selection of different target currencies for the chart   | DONE        |
| Allow the selection of three different time periods for the chart   | DONE        |
| Rates page containing the rates for the entered amount   | DONE        |
| Charts page containing the charts for the currency   | DONE        |




### Story3
`BiometricVerificationFragment` show the biometric popup
`BiometricHelper`contains the necessary functions to make biometric functional

### Story4
Build flavour - User can choose news source from build variant section in Android studio
`news-source` is the flavour dimension 
`NEWS_SOURCE_ID` is a buildConfigField
`NEWS_SOURCE_TITLE` is a buildConfigField

<p float="middle">
<img src="https://github.com/hashir1296/news-app-challenge/blob/a63619a15e6a60298ee106a7a1ecdcd4c5af14c2/Screenshots/Screenshot%202023-06-27%20at%2011.15.14%20PM.png" width = "200" height = "500"/>  
</p>
