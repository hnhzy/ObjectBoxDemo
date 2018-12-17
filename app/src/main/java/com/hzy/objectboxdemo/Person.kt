package com.hzy.objectboxdemo

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 *
Created by hzy on 2018/12/17
 *
 **/
@Entity
data class Person(
    @Id var id: Long = 0,
    var name: String? = null,
    var age: Int? = null,
    var birthday: Date? = null
)