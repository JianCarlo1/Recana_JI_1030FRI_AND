package com.ucb.newsactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class NewsContentFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_content, container, false)
        val title = arguments?.getString("title")
        val content = arguments?.getString("content")
        val imageResId = arguments?.getInt("imageResId") // Get the image resource ID

        view.findViewById<TextView>(R.id.newsTitle).text = title
        view.findViewById<TextView>(R.id.newsContent).text = content
        view.findViewById<ImageView>(R.id.newsImage).setImageResource(imageResId ?: R.drawable.ic_launcher_foreground)

        return view
    }
}

