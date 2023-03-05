package com.laioffer.tinnews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.laioffer.tinnews.model.NewsResponse
import com.laioffer.tinnews.network.NewsApi
import com.laioffer.tinnews.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController



        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        setupWithNavController(navView, navController!!)

        setupActionBarWithNavController(this, navController)


//        // method 2:
//
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//
//        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
//
//        setupWithNavController(navView, navController)
//
//        setupActionBarWithNavController(this, navController)








//        // Add the following code inside onCreate: test， remove to repository
//        val api: NewsApi = RetrofitClient.newInstance().create<NewsApi>(NewsApi::class.java)
//
//        api.getTopHeadlines("US")?.enqueue(object : Callback<NewsResponse?> {
//
//            override fun onResponse(call: Call<NewsResponse?>, response: Response<NewsResponse?>) {
//                if (response.isSuccessful) {
//                    Log.d("getTopHeadlines", response.body().toString())
//                } else {
//                    Log.d("getTopHeadlines", response.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
//                Log.d("getTopHeadlines", t.toString())
//            }
//        })
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}