package com.gabo.ramo.core

import android.app.Application
import io.realm.Realm

class RamoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}