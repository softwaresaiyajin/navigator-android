package com.example.geraldadorza.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.softwaresaiyajin.navigator.manager.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val map = Navigator.shared
                .map(emptyList(), "") {

                }

    }
}
