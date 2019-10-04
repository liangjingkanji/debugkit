package com.hulab.debugkit

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.*

/**
 * Created by Nebneb on 21/03/2017 at 17:14.
 */

class DevTool(private val activity: Activity) : LifecycleObserver {

    companion object {
        var enabled = true

        internal var startX = 0f
        internal var startY = 0f
    }

    private val functions = ArrayList<DevFunction>()

    /**
     * Get the [DevFragment] instance created by the builder.
     */
    private val devFragment: DevFragment = DevFragment()

    var theme: DevFragment.DevToolTheme = DevFragment.DevToolTheme.DARK
    var textSize: Int? = null
    var startX = DevTool.startX
    var startY = DevTool.startY

    /**
     * Add function to the function list. This will generate a button on the devFragment's panel.
     *
     * @param function will be called on the matching button click, and the return String
     * of the function will be logged in the console as soon as the function
     * ended.
     * @return this to allow chaining.
     */
    fun function(title: String? = null, block: DevFunction.() -> Unit) {
        val devFunction = DevFunction(devFragment)
        devFunction.title = title
        devFunction.block = block
        this.functions.add(devFunction)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun close(lifecycleOwner: LifecycleOwner) {
        if (lifecycleOwner is Fragment && lifecycleOwner.isRemoving) {
            devFragment.close()
        }
    }

    /**
     * Build the devFragment and show it.
     *
     * @return this to allow chaining.
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
