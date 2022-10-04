package br.com.jwar.sharedbill.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController
import br.com.jwar.sharedbill.presentation.navigation.BottomNav
import br.com.jwar.sharedbill.presentation.navigation.NavGraph
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            navController = rememberAnimatedNavController(bottomSheetNavigator)
            SharedBillTheme {
                Scaffold(
                    bottomBar = { BottomNav(navController = navController) }
                ) {
                    NavGraph(navController)
                }
            }
        }
    }
}