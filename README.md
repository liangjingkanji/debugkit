# Android DebugKit 
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [ ![Download](https://api.bintray.com/packages/nebneb/DebugKit/debugkit/images/download.svg) ](https://bintray.com/nebneb/DebugKit/debugkit/_latestVersion)

弹出一个可添加按钮和显示打印日志(非系统日志而是本框架函数打印的日志)的悬浮窗, 相当于一个小型控制台



避免需要再界面上添加按钮来响应手动事件的麻烦

直接可以在界面上显示日志

## 要求

* Android SDK 15+



## 功能

* 配置按钮,按钮数量和按钮显示文本
* 亮色|暗色主题模式
* 移动|关闭|最小化悬浮窗
* 打印日志到悬浮窗上
* Kotlin
* 如果Activity和Fragment生命周期销毁悬浮窗关闭

## 安装

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



## 使用

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





## 截图

___
![Screenshot.png](https://github.com/hulab/debugkit/blob/master/resources/screenshot.png)
___
![dark theme](https://github.com/hulab/debugkit/blob/master/resources/theme_dark.gif)
![light theme](https://github.com/hulab/debugkit/blob/master/resources/theme_light.gif)


