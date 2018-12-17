package com.hzy.objectboxdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import io.objectbox.query.Query
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tv_show: TextView

    private lateinit var personBox: Box<Person>
    private lateinit var personQuery: Query<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_show = findViewById(R.id.tv_show)
        personBox = ObjectBox.personInfo.boxFor()
        personQuery = personBox.query { order(Person_.id) }
        updateTv()
    }

    fun OnClicked(v: View) {
        when (v.id) {
            R.id.bt_add -> {//设置为0会自动增长
                val p = Person(0, "hzy", 1, Date())
                personBox.put(p)
                updateTv()
            }
            R.id.bt_update -> {
                val p = Person(1, "hzy100", 121, Date())
                personBox.put(p)
                updateTv()
            }
            R.id.bt_del -> {
                personBox.remove(1)
                updateTv()
            }
            R.id.bt_query -> {
                updateTv()
            }
        }
    }

    fun updateTv() {
        val person = personQuery.find()
        Log.e("notes", person.toString())
        tv_show.setText(person.toString());
    }
}
