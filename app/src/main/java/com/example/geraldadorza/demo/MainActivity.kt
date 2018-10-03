package com.example.geraldadorza.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.softwaresaiyajin.navigator.manager.Navigator
import com.softwaresaiyajin.navigator.model.NavigationEvent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigator.shared
                .map(emptyList(), "") {

                }

    }
}
