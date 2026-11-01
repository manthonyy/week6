
package com.example.week6

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object DashboardRoute

@Serializable
object CommunitiesRoute

@Serializable
data class ClubProjectsRoute(
    val communityId: String
)

@Serializable
data class ProjectDetailRoute(
    val projectId: String
)

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val campusViewModel: CampusViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar =
        currentDestination?.hasRoute<DashboardRoute>() == true ||
                currentDestination?.hasRoute<CommunitiesRoute>() == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {

                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<DashboardRoute>() == true,
                        onClick = {
                            navController.navigate(DashboardRoute) {
                                popUpTo<DashboardRoute> {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Text("⌂")
                        },
                        label = {
                            Text("Dashboard")
                        }
                    )

                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<CommunitiesRoute>() == true,
                        onClick = {
                            navController.navigate(CommunitiesRoute) {
                                popUpTo<DashboardRoute> {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Text("◎")
                        },
                        label = {
                            Text("Communities")
                        }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable<DashboardRoute> {
                DashboardScreen(
                    viewModel = campusViewModel
                )
            }

            composable<CommunitiesRoute> {
                CommunitiesScreen(
                    viewModel = campusViewModel,
                    onOpenCommunity = { communityId ->
                        navController.navigate(
                            ClubProjectsRoute(communityId)
                        )
                    }
                )
            }

            composable<ClubProjectsRoute> { backStackEntry ->

                val route = backStackEntry.toRoute<ClubProjectsRoute>()

                ClubProjectsScreen(
                    viewModel = campusViewModel,
                    communityId = route.communityId,
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenProject = { projectId ->
                        navController.navigate(
                            ProjectDetailRoute(projectId)
                        )
                    }
                )
            }

            composable<ProjectDetailRoute> { backStackEntry ->

                val route = backStackEntry.toRoute<ProjectDetailRoute>()

                ProjectDetailScreen(
                    viewModel = campusViewModel,
                    projectId = route.projectId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
