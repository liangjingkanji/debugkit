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
import kotlinx.android.synthetic.main.debugkit_fragment_dev_tools.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Nebneb on 26/10/2016 at 11:27.
 */

class DevFragment : Fragment(), View.OnTouchListener {


    private var consoleHeight = 110
    private var consoleWidth = 250
    private var consoleTextSize = 12


    private lateinit var inflater: LayoutInflater

    private var functions: MutableList<DevFunction> = ArrayList()


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

        return inflater.inflate(R.layout.debugkit_fragment_dev_tools, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        for (i in functions.indices) {

            val button = inflater.inflate(
                    if (theme == DevToolTheme.DARK) R.layout.debugkit_function_button_dark else R.layout.debugkit_function_button_light,
                    ll_button_container,
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

            ll_button_container.addView(button)
        }



        iv_tools_close_button.setOnClickListener {
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


        view.setOnTouchListener(this)


        var layoutParams = sv_console_scroll_view.layoutParams
        layoutParams.height = dpTopX(consoleHeight)
        sv_console_scroll_view.layoutParams = layoutParams

        layoutParams = tv_console.layoutParams
        layoutParams.height = dpTopX(consoleHeight)
        layoutParams.width = dpTopX(consoleWidth)
        tv_console.layoutParams = layoutParams
        tv_console.minimumHeight = dpTopX(consoleHeight)

        view.x = startX
        view.y = startY

        iv_tools_minify?.apply {
            setTag(id, false)
            setOnClickListener { switchMinify() }
        }

        applyTheme()
        softLog("ready")
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        val temp = view ?: return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = temp.x - event.rawX
                dY = temp.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                temp.x = event.rawX + dX
                temp.y = event.rawY + dY
            }
        }
        return true
    }

    /**
     * Switch the tool to minify mode.
     */
    private fun switchMinify() {

        val rotateAnimation: RotateAnimation
        val heightValueAnimator: ValueAnimator
        val widthValueAnimator: ValueAnimator

        if (iv_tools_minify!!.getTag(iv_tools_minify!!.id) as Boolean) {
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
            iv_tools_minify!!.setTag(iv_tools_minify!!.id, false)
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
            iv_tools_minify!!.setTag(iv_tools_minify!!.id, true)
        }

        heightValueAnimator.duration = 200
        heightValueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            sv_console_scroll_view.layoutParams.height = value
            sv_console_scroll_view.requestLayout()
        }
        widthValueAnimator.duration = 200
        widthValueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            tv_console.layoutParams.width = value
            tv_console.requestLayout()
        }

        rotateAnimation.duration = 200
        rotateAnimation.fillAfter = true
        iv_tools_minify!!.startAnimation(rotateAnimation)
        heightValueAnimator.interpolator = AccelerateInterpolator()
        heightValueAnimator.start()
        widthValueAnimator.interpolator = AccelerateInterpolator()
        widthValueAnimator.start()
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
        val sb = StringBuilder(tv_console.text)
        sb.append("\n")
        sb.append(currentTime).append("   ")
        sb.append(string)
        write(sb.toString())
    }

    /**
     * Call this function at runtime if you want to clear the console.
     */
    fun clear() {
        tv_console.text = ""
        softLog("ready")
    }

    private fun softLog(string: String) {
        val sb = StringBuilder(tv_console.text)
        sb.append(currentTime).append("   ")
        sb.append(string)
        write(sb.toString())
    }

    private fun write(string: String) {
        tv_console.text = string

        tv_console.post {
            tv_console?.requestLayout()
            sv_console_scroll_view?.post {
                sv_console_scroll_view?.fullScroll(ScrollView.FOCUS_DOWN)
                sv_console_scroll_view?.requestLayout()
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
     * Set the tv_console text size. Must be called after having called build()
     *
     * @param sp the size of the text in sp.
     */
    fun changeConsoleTextSize(sp: Int) {
        tv_console.post { tv_console.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp.toFloat()) }
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
                tv_console.setBackgroundColor(getColor(R.color.debug_kit_primary_light))
                tv_console.setTextColor(getColor(R.color.debug_kit_background_black_light))
            }
            else -> {
                tv_console.setBackgroundColor(getColor(R.color.debug_kit_background_black))
                tv_console.setTextColor(getColor(R.color.debug_kit_primary))
            }
        }

        tv_console.setTextSize(TypedValue.COMPLEX_UNIT_SP, consoleTextSize.toFloat())
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
        view?.apply {
            DevTool.startX = x
            DevTool.startY = y
        }

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
