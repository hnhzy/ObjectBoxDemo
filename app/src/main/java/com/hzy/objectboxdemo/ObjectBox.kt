package com.hzy.objectboxdemo

import android.content.Context
import android.os.Environment
import io.objectbox.BoxStore
import java.io.File

/**
 * Singleton to keep BoxStore reference.
 */
object ObjectBox {

    lateinit var personInfo: BoxStore
        private set

    fun build(context: Context) {
        personInfo = MyObjectBox.builder().androidContext(context.applicationContext).build()
    }

}