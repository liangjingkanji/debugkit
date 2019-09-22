package com.hulab.debugkit.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hulab.debugkit.DevFragment
import com.hulab.debugkit.DevTool
import com.hulab.debugkit.dev


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // global switch enabled
        DevTool.enabled = true


        val devTool = dev {

            function {
                log("自定义主题")
                customTheme()
            }


            function("sample") {
                supportFragmentManager.beginTransaction().replace(R.id.content, SampleFragment())
                    .commit()
            }

            function("sample2") {
                supportFragmentManager.beginTransaction().replace(R.id.content, Sample2Fragment())
                    .commit()
            }
        }
    }


    private fun customTheme() {

        val devTool = DevTool(this@MainActivity).apply {
            startY = 300F
            textSize = 12
            theme = DevFragment.DevToolTheme.LIGHT
        }

        devTool.function {

        }

        devTool.build()

//        dev(devTool) {
//
//        }
    }

}