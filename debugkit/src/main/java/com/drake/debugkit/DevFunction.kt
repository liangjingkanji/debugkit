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

/**
 * 该类可以创建一个调试窗口
 */
class DevFunction(private val devFragment: DevFragment) {

    internal lateinit var block: DevFunction.() -> Unit
    internal var defaultLog: Boolean = true
    internal var title: String? = null

    /**
     * 输出一段日志到调试窗口
     * `HH:mm:ss > msg`
     *
     * @param msg 日志内容
     */
    fun log(msg: Any?) {
        defaultLog = false
        devFragment.log(msg)
    }

    /**
     * 清除日志
     */
    fun clear() {
        devFragment.clear()
    }


    /**
     * 关闭调试窗口
     */
    fun close() {
        devFragment.close()
    }

}
