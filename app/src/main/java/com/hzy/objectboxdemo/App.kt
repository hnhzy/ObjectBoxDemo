package com.hzy.objectboxdemo

import android.app.Application

/**
 *
Created by hzy on 2018/12/17
 *
 **/
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.build(this)
    }
}