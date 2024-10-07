package nz.ac.canterbury.seng303.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.ac.canterbury.seng303.lab2.screens.ProductsScreen
import nz.ac.canterbury.seng303.lab2.ui.theme.Lab1Theme
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel as koinViewModel

import nz.ac.canterbury.seng303.lab2.screens.Home
import nz.ac.canterbury.seng303.lab2.screens.StallsScreen
import nz.ac.canterbury.seng303.lab2.viewmodels.MarketViewModel

class MainActivity : ComponentActivity() {

    private val marketViewModel: MarketViewModel by koinViewModel()
    private val stallViewModel: StallViewModel by koinViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Delete the datastore on launch
        if (false) {
            marketViewModel.deleteAll()
            stallViewModel.deleteAll()
        }

        // Load the test data if none exist
        marketViewModel.loadDefaultNotesIfNoneExist()
        stallViewModel.loadDefaultNotesIfNoneExist()

        setContent {
            Lab1Theme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Farmers Market") },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(navController = navController, startDestination = "Home") {
                            composable("Home") {
                                Home(navController= navController, marketViewModel)
                            }
                            composable("ProductsScreen/{stallId}") { backStackEntry ->
                                val stallId = backStackEntry.arguments?.getString("stallId")?.toInt()
                                stallId?.let { ProductsScreen(navController, it, stallViewModel) }
                            }
                            composable("StallsScreen/{marketId}") { backStackEntry ->
                                val marketId = backStackEntry.arguments?.getString("marketId")?.toInt()
                                StallsScreen(navController = navController, stallViewModel = stallViewModel, marketId = marketId)
                            }

                        }
                    }
                }
            }
        }
    }
}




