<p align="center"><img src="https://i.imgur.com/3NdQ93C.png" alt="1600" width="25%"/></p>

<p align="center"><strong>一个调试悬浮窗</strong></p>

<p align="center"><a href="http://liangjingkanji.github.io/debugkit/">使用文档</a></p>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/debugkit"><img src="https://jitpack.io/v/liangjingkanji/debugkit.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="https://jq.qq.com/?_wv=1027&k=vWsXSNBJ"><img src="https://img.shields.io/badge/QQ群-752854893-blue"/></a>
</p>

<p align="center"><img src="https://i.imgur.com/9V87YJi.png" align="center" width="250" /></p>

### 功能

- [x] 配置按钮, 按钮数量和按钮显示文本
- [x] 亮色|暗色主题模式
- [x] 移动|关闭|最小化悬浮窗
- [x] 打印日志到悬浮窗上
- [x] Kotlin DSL
- [x] 悬浮窗跟随Activity/Fragment生命周期



<br>

在项目根目录的 build.gradle 添加仓库

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

在 module 的 build.gradle 添加依赖

```groovy
implementation 'com.github.liangjingkanji:debugkit:1.2.10'
```

<br>

## License

```
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
