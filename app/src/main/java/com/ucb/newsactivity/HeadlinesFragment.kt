package com.ucb.newsactivity

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HeadlinesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_headlines, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        val newsList = listOf(
            NewsArticle("Kotlin", "Kotlin is a modern programming language designed for Android development, emphasizing conciseness and safety.", R.drawable.kotlin),
            NewsArticle("Champion", "Golden State Warriors: Resilient champions redefining basketball excellence and teamwork.", R.drawable.champ),
            NewsArticle("Technology", "Exploring the impact and innovations of leading technology companies globally.", R.drawable.techgiants),
            NewsArticle("Global Warming", "Global Warming: The increasing rise in Earth's temperature due to human activities.", R.drawable.globalwarming),
            NewsArticle("Draconia Saga", "\"Draconia Saga: An epic adventure exploring the realms of dragons, magic, and heroism.", R.drawable.dragon)
        )


        newsAdapter = NewsAdapter(newsList) { newsArticle ->
            val newsContentFragment = NewsContentFragment()
            val args = Bundle()
            args.putString("title", newsArticle.title)
            args.putString("content", newsArticle.content)
            args.putInt("imageResId", newsArticle.imageResId) // Pass the image resource ID
            newsContentFragment.arguments = args

            // Replace the content fragment based on orientation
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_content, newsContentFragment)
                    .commit()
            } else {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newsContentFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = newsAdapter

        // Add DividerItemDecoration with custom drawable
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        recyclerView.addItemDecoration(dividerItemDecoration)


        return view
    }
}


