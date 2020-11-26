/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.sample.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.drake.debugkit.DevFragment
import com.drake.debugkit.dev
import com.drake.sample.R


class ThemeFragment : Fragment(R.layout.fragment_theme) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
    }
}
