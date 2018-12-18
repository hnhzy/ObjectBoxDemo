package com.hzy.objectboxdemo

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class Student {

    @Id
    var id: Long = 0

    var name: String =""

    var teachers: ToMany<Teacher>? = null

    // used by ObjectBox to init relations
    constructor() {}

    constructor(name: String) {
        this.name = name
    }
}
