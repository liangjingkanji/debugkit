package com.hulab.debugkit

import android.app.Activity
import androidx.fragment.app.Fragment

fun Activity.dev(devTool: DevTool? = null, block: DevTool.() -> Unit) {
    val temp = devTool ?: DevTool(this)
    temp.block()
    temp.build()
}

fun Fragment.dev(devTool: DevTool? = null, block: DevTool.() -> Unit) {
    val temp = devTool ?: DevTool(activity ?: return)
    lifecycle.addObserver(temp)
    temp.block()
    temp.build()
}