package com.gabo.ramo.presentation.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView
import androidx.core.os.bundleOf
import com.gabo.ramo.R
import com.gabo.ramo.core.BaseView
import com.gabo.ramo.data.Movie
import com.gabo.ramo.data.MovieCategory
import com.gabo.ramo.presentation.moviedetail.MOVIE_ID_PARAM
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_movie_search.*


class MovieSearchFragment : Fragment(), MovieSearchView, SearchQueryListener, MovieRecyclerViewAdapter.OnListInteractionListener {


    companion object {
        private const val NUMBER_OF_COLUMNS = 2
        @JvmStatic
        fun newInstance(arguments: Bundle): MovieSearchFragment {
            val fragment = MovieSearchFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    lateinit var searchPresenter: MovieSearchPresenter
    lateinit var movieAdapter: MovieRecyclerViewAdapter
    private var listener: BaseView.NavigationListener? = null
    private var isSearching: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onQueryReceived(query: String?) {
        query?.let { searchPresenter?.findMoviesBy(query) }
    }

    override fun onResume() {
        super.onResume()
        searchPresenter?.attachView(this)

        if(isSearching){
            return
        }

        tabs?.let {
            val position = it.selectedTabPosition
            findMovies(position)
        }

    }

    override fun onPause() {
        super.onPause()
        searchPresenter?.detachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_search, container, false)
        movieAdapter = MovieRecyclerViewAdapter(listOf(), this)
        view.findViewById<RecyclerView>(R.id.list)?.apply {
            layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)
            adapter = movieAdapter
        }
        searchPresenter = MovieSearchPresenter(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildTabs()
    }

    private fun buildTabs() {
        tabs.clearOnTabSelectedListeners()
        tabs.removeAllTabs()
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
            searchPresenter.findAllMoviesByCategory(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.options_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search_movies)?.actionView as SearchView).let {
            it.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            it.setOnCloseListener {
                isSearching = false
                tabs?.visibility = View.VISIBLE
                val position = tabs?.selectedTabPosition ?: 0
                findMovies(position)
                true
            }

            it.setOnSearchClickListener {
                isSearching = true
                tabs?.visibility = View.GONE
            }
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseView.NavigationListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListInteractionListener")
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

    override fun onListItemSelected(item: Movie) {
        var params = bundleOf(MOVIE_ID_PARAM to item.id)
        listener?.navigateTo(R.id.fragment_movie_detail, params)

    }

    override fun startSearchQueryAnimation() {

        loading_animation.visibility = View.VISIBLE
        list.visibility = View.GONE
        loading_animation.setAnimation(R.raw.search_ask_loop)
        loading_animation.playAnimation()

    }

    override fun stopSearchQueryAnimation() {
        loading_animation.pauseAnimation()
        loading_animation.visibility = View.GONE
        list.visibility = View.VISIBLE
    }

    override fun startLoadingCategoryAnimation() {
        loading_animation.visibility = View.VISIBLE
        list.visibility = View.GONE
        loading_animation.setAnimation(R.raw.loading)
        loading_animation.playAnimation()
    }

    override fun stopLoadingCategoryAnimation() {
        loading_animation.pauseAnimation()
        loading_animation.visibility = View.GONE
        list.visibility = View.VISIBLE
    }

    override fun showModeSearchingResults(resultsSize: Int) {
        Snackbar.make(tabs, "Search results ($resultsSize)", Snackbar.LENGTH_LONG).show()
    }

}
