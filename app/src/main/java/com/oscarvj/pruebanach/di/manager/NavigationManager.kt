package com.oscarvj.pruebanach.di.manager

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation

class NavigationManager(var context: Context){

    private lateinit var navController: NavController

    fun onNavigateComponents(navController: NavController) {
        this.navController = navController
    }

    fun onNavigate(view: View? = null, idComponent: Int, bundle: Bundle? = null){
        Navigation.findNavController(view!!).navigate(idComponent, bundle)
    }

    fun onNavigateFragment(idComponent: Int, bundle: Bundle? = null){
        try {
            navController.navigate(idComponent, bundle)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun onNavigateBack(){
        navController.navigateUp()
    }

    fun onNavivatePopBackStack(idComponent: Int, inclusive: Boolean){
        navController.popBackStack(idComponent, inclusive)
    }

}
