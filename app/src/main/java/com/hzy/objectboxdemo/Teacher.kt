package com.hzy.objectboxdemo

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class Teacher {

    @Id
    var id: Long = 0

    var name: String =""

    // 'to' is optional if only one relation matches
    @Backlink(to = "teachers")
    var students: ToMany<Student>? = null

    // used by ObjectBox to init relations
    constructor() {}

    constructor(name: String) {
        this.name = name
    }
}
