## Tinder_like news APP (From java to kotlin)

### java version ()

### kotlin version

#### Navigation 
+ dependencies

+ ui.save
    + SaveFragment
+ ui.search
    + SearchFragment 
+ ui.home
    + HomeFragment

+ res/navigation/nav_graph.xml
+ menu
+ navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
   navController = navHostFragment!!.navController
   val navView = findViewById<BottomNavigationView>(R.id.nav_view)
   setupWithNavController(navView, navController!!)

#### network
+ json - kotlin : generate classes
+ network: NewsApi interface, RetrofitClient.class 
+ test in mainactivity

