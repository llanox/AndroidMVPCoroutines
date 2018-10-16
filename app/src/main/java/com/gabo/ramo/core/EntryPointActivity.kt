package com.gabo.ramo.core

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gabo.ramo.R
import com.gabo.ramo.core.extensions.addFragment
import com.gabo.ramo.data.Movie
import com.gabo.ramo.search.MovieFragment
import com.gabo.ramo.search.SearchQueryListener

class EntryPointActivity : AppCompatActivity(), MovieFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: Movie?) {
    }

    override fun onNewIntent(newIntent: Intent) {
        super.onNewIntent(newIntent)
        handleIntent(newIntent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Toast.makeText(this, "Query $query", Toast.LENGTH_LONG).show()
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment is SearchQueryListener) {
                currentFragment?.onQueryReceived(query)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_point_layout)
        handleIntent(intent)
        findViewById<FrameLayout>(R.id.fragment_container)?.let {
            if (savedInstanceState == null) {
                addFragment(MovieFragment.newInstance(), R.id.fragment_container)
            }
        }
    }

}
