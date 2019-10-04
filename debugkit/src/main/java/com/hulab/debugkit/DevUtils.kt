package com.hulab.debugkit

import android.app.Activity
import androidx.fragment.app.Fragment

fun Activity.dev(devTool: DevTool? = null, block: DevTool.() -> Unit) {
    val temp = devTool ?: DevTool(this)
    temp.block()
    temp.build()
}

/**
 *
 * Note:  在Fragment视图销毁(DestroyView)时会被关闭调试窗口
 *
 */
fun Fragment.dev(devTool: DevTool? = null, block: DevTool.() -> Unit) {
    val temp = devTool ?: DevTool(this.activity ?: return)
    lifecycle.addObserver(temp)
    temp.block()
    temp.build()
}