package com.gabo.ramo.presentation

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.gabo.ramo.R
import com.gabo.ramo.core.BaseView
import com.gabo.ramo.core.extensions.addFragment
import com.gabo.ramo.core.extensions.replaceFragment
import com.gabo.ramo.presentation.moviedetail.MovieDetailFragment
import com.gabo.ramo.presentation.search.MovieSearchFragment
import com.gabo.ramo.presentation.search.SearchQueryListener

class EntryPointActivity : AppCompatActivity(), BaseView.NavigationListener {



    override fun onNewIntent(newIntent: Intent) {
        super.onNewIntent(newIntent)
        handleIntent(newIntent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
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
        actionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<FrameLayout>(R.id.fragment_container)?.let {
            if (savedInstanceState == null) {
                navigateTo(R.id.fragment_movie_search, Bundle())
            }
        }

    }

    override fun navigateTo(framentId: Int, data: Bundle) {
        when(framentId){
            R.id.fragment_movie_search -> {
                addFragment(MovieSearchFragment.newInstance(data), R.id.fragment_container)
            }
            R.id.fragment_movie_detail -> replaceFragment(MovieDetailFragment.newInstance(data), R.id.fragment_container)
            else -> replaceFragment(MovieSearchFragment.newInstance(data), R.id.fragment_container)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0 ) finish()
    }


}
