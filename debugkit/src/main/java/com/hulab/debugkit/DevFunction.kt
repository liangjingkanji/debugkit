package com.hulab.debugkit

import java.util.concurrent.Callable

/**
 * Created by Nebneb on 22/03/2017 at 18:18.
 */

/**
 * Use this class to add a function to the debug tool. The return type of `call()` method will
 * be logged to the console.
 *
 * @see Callable
 */
class DevFunction(private val devFragment: DevFragment) {

    internal lateinit var block: DevFunction.() -> Unit
    internal var defaultLog: Boolean = true
    internal var title: String? = null


    /**
     * Calling this method will msg a message in the console.
     *
     * @param msg the message that will be logged to to the console.
     *
     *
     * msg will be logged in the console on a new line as following:
     * <br></br>
     * `HH:mm:ss > msg`
     */
    fun log(msg: String) {
        defaultLog = false
        devFragment.log(msg)
    }

    /**
     * Calling this method will clear the console.
     */
    fun clear() {
        devFragment.clear()
    }


}
