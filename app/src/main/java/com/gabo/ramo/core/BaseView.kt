package com.gabo.ramo.core

import android.os.Bundle
import androidx.annotation.IdRes

interface BaseView{
    interface NavigationListener{
        fun navigateTo(@IdRes framentId:Int, data: Bundle)
    }

}