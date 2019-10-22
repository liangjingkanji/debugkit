# Android DebugKit 
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [ ![Download](https://api.bintray.com/packages/nebneb/DebugKit/debugkit/images/download.svg) ](https://bintray.com/nebneb/DebugKit/debugkit/_latestVersion)

DebugKit lib for Android allows you to use a fancy hovering debug tool to trigger some actions directly in the app. This tool is very useful to trigger some event at runtime, and to have a written feedback directly on your testing phone screen.

## Requirements

* Android SDK 15+



### Fork Feature

- Support Activity
- Support Fragment (Jetpack Navigation)
- Kotlin





# Installation

project of build.gradle

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```



module of build.gradle

```groovy
implementation 'com.github.liangjingkanji:debugkit:1.2.9'
```



# Usage

```kotlin
dev {

  function {
    log("Some stuff was done.")
  }

  function("title") {
    // do something
  }
}
```

## Result

Here we have 3 debug tools, from top to bottom:


* LIGHT theme with 5 defined functions
* Minified mode (clicking on the top left corner arrow)
* DARK theme with 3 defined functions and the logged text when clicking on each F1, F2 and F3
___
![Screenshot.png](https://github.com/hulab/debugkit/blob/master/resources/screenshot.png)
___
![dark theme](https://github.com/hulab/debugkit/blob/master/resources/theme_dark.gif)
![light theme](https://github.com/hulab/debugkit/blob/master/resources/theme_light.gif)
___

**Have fun!**
