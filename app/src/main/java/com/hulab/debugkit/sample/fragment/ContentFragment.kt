/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：debugkit
 * Author：Drake
 * Date：10/3/19 7:47 PM
 */

package com.hulab.debugkit.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.hulab.debugkit.sample.R


class ContentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_content, container, false)
    }

}
