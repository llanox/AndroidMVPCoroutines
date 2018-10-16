package com.gabo.ramo.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView
import com.gabo.ramo.R
import com.gabo.ramo.data.Movie
import com.gabo.ramo.data.MovieCategory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_movie_list.*


class MovieFragment : Fragment(), SearchMovieView, SearchQueryListener {

    companion object {
        private const val NUMBER_OF_COLUMNS = 2
        @JvmStatic
        fun newInstance() = MovieFragment()
    }

    lateinit var presenter: SearchMoviePresenter
    private var listener: OnListFragmentInteractionListener? = null
    lateinit var movieAdapter: MovieRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onQueryReceived(query: String?) {
        query?.let { presenter?.findMoviesBy(query) }
    }

    override fun onResume() {
        super.onResume()
        presenter?.attachView(this)
        tabs?.let {
            val position = it.selectedTabPosition
            findMovies(position)
        }


    }

    override fun onPause() {
        super.onPause()
        presenter?.detachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        movieAdapter = MovieRecyclerViewAdapter(listOf(), listener)
        view.findViewById<RecyclerView>(R.id.list)?.apply {
            layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)
            adapter = movieAdapter
        }
        presenter = SearchMoviePresenter(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (category in MovieCategory.values()) {
            tabs.addTab(tabs.newTab().setText(category.label), category.position)
        }

        tabs.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val position = it.position
                    findMovies(position)
                }
            }
        })

    }

    private fun findMovies(position: Int): Unit? {
        return MovieCategory.values().find { it.position == position }?.let {
            presenter.findAllMoviesByCategory(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.options_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search_movies)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun updateListOfMovies(movies: List<Movie>) {
        movieAdapter.updateMovies(movies)
    }

    override fun showErrorFetchingMovies(errorMsg: String) {
        view?.let {
            Snackbar.make(it, errorMsg, Snackbar.LENGTH_LONG).show()
        }
    }



    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Movie?)
    }


}
