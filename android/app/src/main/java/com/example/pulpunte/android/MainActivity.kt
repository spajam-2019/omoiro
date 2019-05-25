package com.example.pulpunte.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.IO

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val socket = IO.socket("https://c8a5d154.ngrok.io").connect()
    }
}
