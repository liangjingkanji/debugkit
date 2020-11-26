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

package com.drake.debugkit

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.*

class DevTool(val activity: Activity) : LifecycleObserver {

    companion object {
        var enabled = true // 是否显示调试窗口
        internal var startX = 0f
        internal var startY = 0f
    }

    private val functions = ArrayList<DevFunction>()
    private val devFragment: DevFragment = DevFragment()

    var theme: DevFragment.DevToolTheme = DevFragment.DevToolTheme.DARK
    var textSize: Int? = null
    var startX = DevTool.startX
    var startY = DevTool.startY

    /**
     * 添加一个按钮到调试窗口
     * @param title 按钮标题
     * @param block 点击按钮回调函数
     */
    fun function(title: String? = null, block: DevFunction.() -> Unit) {
        val devFunction = DevFunction(devFragment)
        devFunction.title = title
        devFunction.block = block
        this.functions.add(devFunction)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun close(lifecycleOwner: LifecycleOwner) {
        if (lifecycleOwner is Fragment && lifecycleOwner.isRemoving) {
            devFragment.close()
        }
    }

    /**
     * 构建一个DevFragment
     */
    fun build() {
        if (!enabled) {
            return
        }
        if (functions.size > 0)
            devFragment.setFunctionList(functions)
        if (textSize != null)
            devFragment.setConsoleTextSize(textSize!!)
        devFragment.displayAt(startX, startY)
        try {
            val fragmentManager = activity.fragmentManager
            fragmentManager.beginTransaction()
                .add(android.R.id.content, devFragment)
                .commit()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        devFragment.setTheme(theme)
    }

}
