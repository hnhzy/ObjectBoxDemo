package com.hzy.objectboxdemo

import android.content.Context
import android.os.Environment
import io.objectbox.BoxStore
import java.io.File

/**
 * Singleton to keep BoxStore reference.
 */
object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun build(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
    }

}