package com.hulab.debugkit

import android.app.Activity
import java.util.*

/**
 * Created by Nebneb on 21/03/2017 at 17:14.
 */

const val devToolsEnabled = true

fun Activity.dev(devTool: DevTool? = null, block: DevTool.() -> Unit) {

    val temp = devTool ?: DevTool(this)
    temp.block()
    temp.build()

}

class DevTool(private val activity: Activity) {

    private val functions = ArrayList<DevFunction>()

    /**
     * Get the [DevFragment] instance created by the builder.
     */
    private val tool: DevFragment = DevFragment()

    var theme: DevFragment.DevToolTheme = DevFragment.DevToolTheme.DARK
    var textSize: Int? = null
    var startX = 0f
    var startY = 0f

    /**
     * Add function to the function list. This will generate a button on the tool's panel.
     *
     * @param function will be called on the matching button click, and the return String
     * of the function will be logged in the console as soon as the function
     * ended.
     * @return this to allow chaining.
     */
    fun function(title: String? = null, block: DevFunction.() -> Unit) {
        val devFunction = DevFunction(tool)
        devFunction.title = title
        devFunction.block = block
        this.functions.add(devFunction)
    }

    /**
     * Build the tool and show it.
     *
     * @return this to allow chaining.
     */
    fun build() {
        if (functions.size > 0)
            tool.setFunctionList(functions)
        if (textSize != null)
            tool.setConsoleTextSize(textSize!!)
        tool.displayAt(startX, startY)
        try {
            val fragmentManager = activity.fragmentManager
            fragmentManager.beginTransaction()
                .add(android.R.id.content, tool)
                .commit()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        tool.setTheme(theme)
    }

}
