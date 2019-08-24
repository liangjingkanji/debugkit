package com.hulab.debugkit

import android.app.Activity
import android.app.Fragment

fun Activity.dev(devTool: DevTool? = null, block: DevTool.() -> Unit) {

    val temp = devTool ?: DevTool(this)
    temp.block()
    temp.build()

}

fun Fragment.dev(devTool: DevTool? = null, block: DevTool.() -> Unit) {

    val temp = devTool ?: DevTool(this.activity)
    temp.block()
    temp.build()

}