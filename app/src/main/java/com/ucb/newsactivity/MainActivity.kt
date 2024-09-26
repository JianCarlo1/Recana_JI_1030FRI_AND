package com.ucb.newsactivity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check for orientation
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Load both fragments for landscape
            findViewById<LinearLayout>(R.id.landscape_container).visibility = View.VISIBLE
            findViewById<FrameLayout>(R.id.fragment_container).visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_headlines, HeadlinesFragment(), "HEADLINES_FRAGMENT")
                .commit()

            // Initially, the content fragment can be empty or set to a default view
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_content, NewsContentFragment(), "NEWS_CONTENT_FRAGMENT")
                .commit()
        } else {
            // Load only the headlines fragment for portrait
            findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.landscape_container).visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HeadlinesFragment(), "HEADLINES_FRAGMENT")
                .commit()
        }
    }
}
