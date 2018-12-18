package com.hzy.objectboxdemo

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Order {
    @Id
    var id: Long = 0
    var customer: ToOne<Customer>? = null
}