package com.gabo.ramo.search

interface SearchQueryListener {
    fun onQueryReceived(query: String?)
}