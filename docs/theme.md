可配置选项

| 函数 | 描述 |
|-|-|
| startX | 主题 |
| startX/startY | 窗口显示位置坐标 |
| textSize | 窗口内容文字大小(单位/dp), 默认12dp |

示例代码

```kotlin
dev {
    theme = DevFragment.DevToolTheme.LIGHT // 调试窗口亮色主题
    // theme = DevFragment.DevToolTheme.DARK // 调试窗口暗色主题
    startX = 300F // 调试窗口开始显示坐标位置
    startY = 500F
    textSize = 10 // 窗口输出内容字体大小 单位/dp

    function {
        log("输出一段日志内容")
    }
}
```