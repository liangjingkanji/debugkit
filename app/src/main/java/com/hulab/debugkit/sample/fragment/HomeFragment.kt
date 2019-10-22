/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：debugkit
 * Author：Drake
 * Date：10/3/19 7:46 PM
 */

package com.hulab.debugkit.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hulab.debugkit.DevFragment
import com.hulab.debugkit.DevTool
import com.hulab.debugkit.dev
import com.hulab.debugkit.sample.R


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // global switch enabled
        DevTool.enabled = true


        dev {

            function {
                log("自定义主题")
                customTheme()
            }


            function("contentFragment") {
                findNavController().navigate(R.id.contentFragment)
            }

            function {
                log(findNavController())
            }
        }

    }

    private fun customTheme() {

        val devTool = DevTool(activity!!).apply {
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
