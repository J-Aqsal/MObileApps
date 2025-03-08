package com.example.project4

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project4.viewmodel.DataViewModel
import com.example.project4.ui.theme.Project4Theme
import com.example.project4.viewmodel.ProfileViewModel
import com.proyek.project4.ui.navigation.NavigationItem
import com.proyek.project4.ui.navigation.Screen
import com.example.project4.ui.screen.dataEntry.DataEntryScreen
import com.example.project4.ui.screen.dataList.DataListScreen
import com.example.project4.ui.screen.edit.EditScreen
import com.example.project4.ui.screen.home.HomeScreen
import com.example.project4.ui.screen.profile.ProfileScreen

@Composable
fun Project4App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: DataViewModel,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailReward.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Profile.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.DataEntry.route) {
                DataEntryScreen(viewModel = viewModel)
            }
            composable(Screen.Edit.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }))
            { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                EditScreen(navController = navController, viewModel = viewModel, dataId = id)
            }
            composable(Screen.DataList.route) {
                DataListScreen(navController = navController, viewModel = viewModel)
            }
            composable(Screen.Profile.route) {
                    ProfileScreen(viewModel = profileViewModel)
            }
            composable(Screen.Home.route){
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}


//private fun shareOrder(context: Context, summary: String) {
//    val intent = Intent(Intent.ACTION_SEND).apply {
//        type = "text/plain"
//        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.project4_reward))
//        putExtra(Intent.EXTRA_TEXT, summary)
//    }
//
//    context.startActivity(
//        Intent.createChooser(
//            intent,
//            context.getString(R.string.project4_reward)
//        )
//    )
//}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "home",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "entry",
                icon = Icons.Default.Add,
                screen = Screen.DataEntry
            ),
            NavigationItem(
                title = "list",
                icon = Icons.Default.Menu,
                screen = Screen.DataList
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
//                selected = false,
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun project4AppPreview() {
        Project4Theme {
            Project4App(
                viewModel = viewModel()
            )
    }
}

