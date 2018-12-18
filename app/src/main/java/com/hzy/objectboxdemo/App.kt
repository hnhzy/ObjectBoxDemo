package com.hzy.objectboxdemo

import android.app.Application
import io.objectbox.BoxStore

/**
 *
Created by hzy on 2018/12/17
 *
 **/
class App : Application() {

    var boxStore: BoxStore? = null
        private set

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this@App).build()
    }

    companion object {
        val TAG = "Relations"
    }
}
