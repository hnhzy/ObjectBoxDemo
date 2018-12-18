package com.hzy.objectboxdemo

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class Customer {
    @Id
    var id: Long = 0
    // 'to' is optional if only one relation matches
    @Backlink(to = "customer")
    var orders: ToMany<Order>? = null
}