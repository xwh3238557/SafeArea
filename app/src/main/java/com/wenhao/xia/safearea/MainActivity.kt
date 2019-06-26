package com.wenhao.xia.safearea

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.wenhao.xia.safearea.helper.oversystemui.AppCompatOverSystemUIHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val overSystemUIHelper = AppCompatOverSystemUIHelper()
        overSystemUIHelper.setOverSystemUi(window, true)

        overSystemUIHelper.setStatusBarBackgroundColor(window, Color.YELLOW)
        overSystemUIHelper.setNavigationBarBackgroundColor(window, Color.DKGRAY)

        setContentView(R.layout.activity_main)


    }
}
