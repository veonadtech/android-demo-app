package com.veon.demoapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.veon.demoapp.databinding.ActivityRecyclerBinding


class RecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)


        initRecycler()
    }

    private fun initRecycler() {
        findViewById<RecyclerView>(R.id.recyclerview).apply {
            val adapter = SampleRecyclerAdapter()
            adapter.itemsCount = 100
            setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
    }

}