package com.gabo.ramo.core

import androidx.annotation.NonNull

abstract class BasePresenter<V : BaseView> {

    var view : V? = null

    fun attachView(@NonNull view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}