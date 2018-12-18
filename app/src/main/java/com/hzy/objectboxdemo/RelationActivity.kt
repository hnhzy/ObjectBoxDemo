package com.hzy.objectboxdemo

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

/** ObjectBox relations example (https://docs.objectbox.io/relations)  */
class RelationActivity : Activity() {

    private var textViewLog: TextView? = null
    private var store: BoxStore? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.relation_main)

        textViewLog = findViewById(R.id.textViewLog)

        store = (application as App).boxStore
        val start = System.currentTimeMillis()
        ordersAndCustomers()
        studentsAndTeachers()
        val time = System.currentTimeMillis() - start
        log("\n执行时间：" + time + "ms")
    }

    /**
     * 演示：
     * 一对多关联 one-to-many (1:N) relation.
     * 添加一个Order到Customer内
     * 添加多个Order到Customer内
     */
    private fun ordersAndCustomers() {
        val customerBox = store!!.boxFor(Customer::class)
        val orderBox = store!!.boxFor(Order::class)

        //清空Box
        customerBox.removeAll()
        orderBox.removeAll()

        logTitle("添加一个order到customer内 to-one（一对一）")
        val customer = Customer()
        val order1 = Order()
        order1.customer!!.target = customer
        orderBox.put(order1)
        logOrders(orderBox, customer)

        logTitle("添加两个orders到customer内 to-many（一对多）")
        customer.orders!!.reset() //只是为了安全起见
        customer.orders!!.add(Order())
        customer.orders!!.add(Order())
        customerBox.put(customer)
        logOrders(orderBox, customer)

        logTitle("删除第一个order对象")
        orderBox.remove(order1)
        logOrders(orderBox, customer) //customer包含order2、order3

        logTitle("删除掉一个order(没有删除全部的order对象)")
        customer.orders!!.reset()
        customer.orders!!.removeAt(0)
        customerBox.put(customer)
        logOrders(orderBox, customer)//customer包含order3，只删除了order2
    }

    private fun logOrders(orderBox: Box<Order>, customer: Customer) {
        val ordersQueried = orderBox.query().equal(Order_.customerId, customer.id).build().find()
        log("Customer " + customer.id + " has " + ordersQueried.size + " orders")
        for (order in ordersQueried) {
            log("Order " + order.id + " related to customer " + order.customer!!.targetId)
        }
        log("")
    }

    /**
     * 演示：
     * 多对多关联
     */
    private fun studentsAndTeachers() {
        val studentBox = store!!.boxFor(Student::class)
        val teacherBox = store!!.boxFor(Teacher::class)

        //清空Box
        studentBox.removeAll()
        teacherBox.removeAll()

        logTitle("添加两个学生两个老师对，第一个学生对象包含两个老师，第二学生对象包含一个老师")
        val obiWan = Teacher("Obi-Wan Kenobi")
        val yoda = Teacher("Yoda")

        val luke = Student("Luke Skywalker")
        luke.teachers!!.add(obiWan)
        luke.teachers!!.add(yoda)

        val anakin = Student("Anakin Skywalker")
        anakin.teachers!!.add(obiWan)
        studentBox.put(luke, anakin)
        logTeachers(studentBox, teacherBox)

        // https://docs.objectbox.io/queries
        logTitle("查询所有名字叫\"Skywalker\" 老师叫 \"Yoda\"学生")
        val builder = studentBox.query().contains(Student_.name, "Skywalker")
        builder.link(Student_.teachers).equal(Teacher_.name, yoda.name)
        val studentsTaughtByYoda = builder.build().find()
        log(
            "打印集合返回的长度：" + studentsTaughtByYoda.size + "，Yoda老师的学生student有: "
                    + studentsTaughtByYoda[0].name
        )
        log("")

        logTitle("删除老师从老师表中")
        luke.teachers!!.remove(obiWan)
        luke.teachers!!.applyChangesToDb() // 比studentBox.put(student1)效率更高;
        logTeachers(studentBox, teacherBox)

        logTitle("删除第二个老师从学生表中")
        yoda.students!!.clear()
        yoda.students!!.applyChangesToDb()
        logTeachers(studentBox, teacherBox)
    }

    private fun logTeachers(studentBox: Box<Student>, teacherBox: Box<Teacher>) {
        log("There are " + teacherBox.count() + " teachers")
        val students = studentBox.all
        for (student in students) {
            val teachersToMany = student.teachers
            for (teacher in teachersToMany!!) {
                log("Student " + student.id + " is taught by teacher " + teacher.id)
            }
        }
        log("")
    }

    private fun log(message: String) {
        Log.d(App.TAG, message)
        textViewLog!!.append(message + "\n")
    }

    private fun logTitle(message: String) {
        Log.d(App.TAG, ">>> $message <<<")
        val spannableString = SpannableString(message + "\n")
        val span = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(span, 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textViewLog!!.append(spannableString)
    }

}