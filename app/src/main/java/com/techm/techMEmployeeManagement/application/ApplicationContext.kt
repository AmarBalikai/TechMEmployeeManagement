package com.techm.techMEmployeeManagement.application

import android.app.Application
import android.content.Context

/**
 * This class for creating creating application context
 */
class ApplicationContext : Application() {

    companion object {
        lateinit var context: Context
    }

    /**
     * This method is used to initialize the context
     */
    override fun onCreate() {
        super.onCreate()
        context = this

    }
}