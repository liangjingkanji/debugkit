本工具是快速显示一个悬浮的`调试窗口`, 用于输出日志和触发交互事件

## 创建并显示调试窗口

<img src="https://i.imgur.com/9V87YJi.png" width="250"/>

可以在`Activity/Fragment`中使用`dev`函数即可创建并显示一个调试窗口

```kotlin
dev {
    function {
        // do something ...
    }
    function("自定义") {
        // do something ...
    }
    function("关闭") {
        // do something ...
        close() // 关闭调试窗口
    }
}
```

> 调试窗口本质上是一个Fragment, 所以需要使用Activity或Fragment来创建并显示

## 全局配置调试窗口显示开关

调试窗口不应该出现在线上环境, 建议配置该选项, 以防忘记注释或者删除调试窗口

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DevTool.enabled = BuildConfig.DEBUG // 全局调试窗口显示开关
    }
}
```