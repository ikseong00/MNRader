package com.example.mnrader.model

sealed class Routes (val route: String, val isRoot: Boolean = true) {
    object Home : Routes("Home")
    object Animals : Routes("Animals")
    object AddAnimals : Routes("AddAnimals", isRoot = false)
    object MyPage : Routes("MyPage")

    companion object{
        fun getRoutes(route:String): Routes {
            return when(route){
                Home.route -> Home
                Animals.route -> Animals
                AddAnimals.route -> AddAnimals
                MyPage.route -> MyPage
                else -> Home
            }
        }
    }
}