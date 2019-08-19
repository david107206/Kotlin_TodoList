package com.dki.toodolist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val realmResult = realm.where<Todo>()
            .findAll()
            .sort("date" , Sort.DESCENDING)

        val adapter = TodoListAdapter(realmResult)
        listView.adapter = adapter

      /*  realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }
        listView.setOnItemClickListener { parent, view, position, id ->
            Log.d("MainActivity" , "when click id : " + id)
            startActivity<EditActivity>("id" to id)
        }*/
        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }
        listView.setOnItemClickListener { parent, view, position, id ->
            Log.d("MainActivity" , "when click id : " + id)
            // 할 일 수정
            startActivity<EditActivity>("id" to id)
        }

        fab.setOnClickListener {
            startActivity<EditActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
