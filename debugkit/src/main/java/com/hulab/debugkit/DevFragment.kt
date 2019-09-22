package com.hulab.debugkit

import android.animation.ValueAnimator
import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Nebneb on 26/10/2016 at 11:27.
 */

class DevFragment : Fragment() {
    private var consoleHeight = 110
    private var consoleWidth = 250
    private var consoleTextSize = 12

    private var rootView: View? = null

    private var inflater: LayoutInflater? = null

    private var functions: MutableList<DevFunction> = ArrayList()

    private var console: TextView? = null
    private var consoleContainer: ScrollView? = null

    private var panel: View? = null

    private var minifyButton: View? = null

    private var dX: Float = 0.toFloat()
    private var dY: Float = 0.toFloat()

    private var startX = 0f
    private var startY = 0f

    private var theme = DevToolTheme.DARK

    private val currentTime: String
        get() {
            val df = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return df.format(Calendar.getInstance().time)
        }

    internal fun displayAt(x: Float, y: Float) {
        this.startX = x
        this.startY = y
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        rootView = inflater.inflate(R.layout.debugkit_fragment_dev_tools, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mButtonContainer =
            rootView!!.findViewById<View>(R.id.debugkit_button_container) as LinearLayout

        for (i in functions.indices) {

            val button = inflater!!
                .inflate(
                    if (theme == DevToolTheme.DARK) R.layout.debugkit_function_button_dark else R.layout.debugkit_function_button_light,
                    mButtonContainer,
                    false
                ) as Button
            val function = functions[i]
            val title = if (function.title == null) "F" + (i + 1) else function.title

            if (function.title != null) {
                val params = button.layoutParams
                params.width = LinearLayout.LayoutParams.WRAP_CONTENT
                button.layoutParams = params
            }

            button.text = title

            button.setOnClickListener {
                try {
                    function.block(function)
                    if (function.defaultLog) {
                        log("$title: end")
                    }
                } catch (e: Exception) {
                    log("$title: see logcat for more details")
                    e.printStackTrace()
                }
            }

            mButtonContainer.addView(button)
        }

        console = rootView!!.findViewById<View>(R.id.debugkit_console) as TextView
        consoleContainer =
            rootView!!.findViewById<View>(R.id.debugkit_console_scroll_view) as ScrollView

        minifyButton = rootView!!.findViewById(R.id.debugkit_tools_minify)

        panel = rootView!!.findViewById(R.id.debugkit_tools_panel)

        rootView!!.findViewById<View>(R.id.debugkit_tools_close_button).setOnClickListener {
            if (isAdded) {
                try {
                    activity!!.fragmentManager
                        .beginTransaction()
                        .remove(this@DevFragment)
                        .commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        rootView!!.setOnTouchListener { v, event -> this@DevFragment.onTouch(v, event) }


        var layoutParams = consoleContainer!!.layoutParams
        layoutParams.height = dpTopX(consoleHeight)
        consoleContainer!!.layoutParams = layoutParams

        layoutParams = console!!.layoutParams
        layoutParams.height = dpTopX(consoleHeight)
        layoutParams.width = dpTopX(consoleWidth)
        console!!.layoutParams = layoutParams
        console!!.minimumHeight = dpTopX(consoleHeight)

        view.x = startX
        view.y = startY

        minifyButton!!.setTag(minifyButton!!.id, false)
        minifyButton!!.setOnClickListener { switchMinify() }
        applyTheme()

        softLog("ready")
    }

    /**
     * Switch the tool to minify mode.
     */
    private fun switchMinify() {

        val rotateAnimation: RotateAnimation
        val heightValueAnimator: ValueAnimator
        val widthValueAnimator: ValueAnimator

        if (minifyButton!!.getTag(minifyButton!!.id) as Boolean) {
            rotateAnimation = RotateAnimation(
                180f,
                0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            heightValueAnimator = ValueAnimator.ofInt(0, dpTopX(consoleHeight))
            widthValueAnimator = ValueAnimator.ofInt(dpTopX(MINIFY_WIDTH), dpTopX(consoleWidth))
            minifyButton!!.setTag(minifyButton!!.id, false)
        } else {
            rotateAnimation = RotateAnimation(
                0f,
                180f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            heightValueAnimator = ValueAnimator.ofInt(dpTopX(consoleHeight), 0)
            widthValueAnimator = ValueAnimator.ofInt(dpTopX(consoleWidth), dpTopX(MINIFY_WIDTH))
            minifyButton!!.setTag(minifyButton!!.id, true)
        }

        heightValueAnimator.duration = 200
        heightValueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            consoleContainer!!.layoutParams.height = value
            consoleContainer!!.requestLayout()
        }
        widthValueAnimator.duration = 200
        widthValueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            console!!.layoutParams.width = value
            console!!.requestLayout()
        }

        rotateAnimation.duration = 200
        rotateAnimation.fillAfter = true
        minifyButton!!.startAnimation(rotateAnimation)
        heightValueAnimator.interpolator = AccelerateInterpolator()
        heightValueAnimator.start()
        widthValueAnimator.interpolator = AccelerateInterpolator()
        widthValueAnimator.start()
    }


    private fun onTouch(v: View, event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                dX = v.x - event.rawX
                dY = v.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                v.x = event.rawX + dX
                v.y = event.rawY + dY
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> return false
        }

        return true
    }

    /**
     * Call this function at runtime if you want to msg something in the console.
     *
     * @param string the message that will be logged to to the console.
     *
     *
     * string will be logged in the console on a new line as following:
     * <br></br>
     * `HH:mm:ss > string`
     */
    fun log(string: String) {
        val sb = StringBuilder(console!!.text)
        sb.append("\n")
        sb.append(currentTime).append("   ")
        sb.append(string)
        write(sb.toString())
    }

    /**
     * Call this function at runtime if you want to clear the console.
     */
    fun clear() {
        console!!.text = ""
        softLog("ready")
    }

    private fun softLog(string: String) {
        val sb = StringBuilder(console!!.text)
        sb.append(currentTime).append("   ")
        sb.append(string)
        write(sb.toString())
    }

    private fun write(string: String) {
        console!!.text = string
        console!!.post {
            console!!.requestLayout()
            if (consoleContainer != null) {
                consoleContainer!!.post {
                    consoleContainer!!.fullScroll(ScrollView.FOCUS_DOWN)
                    consoleContainer!!.requestLayout()
                }
            }
        }
    }


    /**
     * Add a function to the list. This will add a button as well when calling `build()`
     *
     * @param function must implement [DevFunction].
     */
    fun addFunction(function: DevFunction) {
        this.functions.add(function)
    }

    /**
     * Set the function list. This will corresponding buttons when calling `build()`
     *
     * @param functions must be a List of [DevFunction].
     */
    fun setFunctionList(functions: MutableList<DevFunction>) {
        this.functions = functions
    }

    /**
     * Set the console text size. Must be called after having called build()
     *
     * @param sp the size of the text in sp.
     */
    fun changeConsoleTextSize(sp: Int) {
        console!!.post { console!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp.toFloat()) }
    }

    /**
     * Set the console text size. The size will be applied on build.
     *
     * @param sp the size of the text in sp.
     */
    fun setConsoleTextSize(sp: Int) {
        consoleTextSize = sp
    }

    /**
     * Set the theme of the debug tool
     *
     * @param theme can be `DevToolTheme.LIGHT` or `DevToolTheme.DARK`.
     * The default theme is `DevToolTheme.DARK`
     */
    fun setTheme(theme: DevToolTheme) {
        this.theme = theme
    }


    /**
     * This method will be called on build. You can call this method if you want to change the
     * theme of the console theme at runtime.
     */
    private fun applyTheme() {
        when (theme) {
            DevToolTheme.LIGHT -> {
                console!!.setBackgroundColor(getColor(R.color.debug_kit_primary_light))
                console!!.setTextColor(getColor(R.color.debug_kit_background_black_light))
            }
            else -> {
                console!!.setBackgroundColor(getColor(R.color.debug_kit_background_black))
                console!!.setTextColor(getColor(R.color.debug_kit_primary))
            }
        }

        console!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, consoleTextSize.toFloat())
    }

    private fun getColor(resId: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(resId, null)
        } else {
            resources.getColor(resId)
        }
    }

    private fun dpTopX(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).roundToInt()
    }

    /**
     * Set the console height.
     *
     * @param consoleHeight represents the console height in dp.
     */
    fun setConsoleHeight(consoleHeight: Int) {
        this.consoleHeight = consoleHeight
    }


    internal fun close() {
        val fragmentManager = fragmentManager ?: return
        fragmentManager.beginTransaction().remove(this).commit()
    }


    /**
     * Set the console width.
     *
     * @param consoleWidth represents the console width in dp.
     */
    fun setConsoleWidth(consoleWidth: Int) {
        this.consoleWidth = consoleWidth
    }

    /**
     * Enum, theme choices for the debug tool.
     */
    enum class DevToolTheme {
        DARK,
        LIGHT
    }

    companion object {

        private val MINIFY_WIDTH = 132
    }
}
