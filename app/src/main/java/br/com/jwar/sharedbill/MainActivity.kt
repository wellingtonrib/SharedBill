package br.com.jwar.sharedbill

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.navigation.NavGraph
import br.com.jwar.sharedbill.ui.AppBottomBar
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)
            SharedBillTheme {
                Scaffold(
                    bottomBar = { AppBottomBar(navController = navController) }
                ) {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}