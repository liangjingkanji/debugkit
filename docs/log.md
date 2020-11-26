既然叫调试窗口当然可以输出日志

```kotlin
dev {
    function {
        log("输出一段日志内容")
    }
    function {
        log(null)
    }
}
```