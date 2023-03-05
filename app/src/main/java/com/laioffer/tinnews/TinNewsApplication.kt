package com.laioffer.tinnews

import androidx.room.Room.databaseBuilder

import android.app.Application
import com.facebook.stetho.Stetho

import com.laioffer.tinnews.database.TinNewsDatabase

class TinNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //        Gander.setGanderStorage(GanderIMDB.getInstance());
        Stetho.initializeWithDefaults(this)
        database = databaseBuilder(this, TinNewsDatabase::class.java, "tinnews_db").build()
    }

    companion object {
        @JvmStatic
        var database: TinNewsDatabase? = null
            private set
    }
}