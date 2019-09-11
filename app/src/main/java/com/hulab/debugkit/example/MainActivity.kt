package com.hulab.debugkit.example

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import com.hulab.debugkit.DevFragment
import com.hulab.debugkit.DevTool


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val textSize = 12
    private lateinit var seekbar: SeekBar
    private var theme: DevFragment.DevToolTheme = DevFragment.DevToolTheme.DARK

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = initView()

        // global switch
//        DevTool.enabled = false

        fab?.setOnClickListener {

            val devTool = DevTool(this@MainActivity).apply {
                textSize = this@MainActivity.textSize
                theme = this@MainActivity.theme
            }

            for (i in 0 until seekbar.progress) {
                devTool.function {
                    // do something
                }
            }

            devTool.build()
        }

    }

    private fun initView(): FloatingActionButton? {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val functionNumber = findViewById<TextView>(R.id.functions_number)
        val themeSpinner = findViewById<Spinner>(R.id.theme_spinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.debugkit_themes,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = adapter
        themeSpinner.onItemSelectedListener = this

        seekbar = findViewById(R.id.seekBar)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                functionNumber.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        functionNumber.text = seekbar.progress.toString()
        return fab
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        theme =
            if (position == 0) DevFragment.DevToolTheme.DARK else DevFragment.DevToolTheme.LIGHT
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        theme = DevFragment.DevToolTheme.DARK
    }
}