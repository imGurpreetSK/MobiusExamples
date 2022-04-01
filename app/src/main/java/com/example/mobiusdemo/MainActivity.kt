package com.example.mobiusdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobiusdemo.counter.CounterFragment
import com.example.mobiusdemo.stranger.StrangerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, CounterFragment(), "fragment")
            .commit()
    }
}
