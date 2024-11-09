package com.thoughtworks.visionassistant.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thoughtworks.visionassistant.app.di.Dependency
import com.thoughtworks.visionassistant.app.ui.views.abilityconfig.AbilityConfigScreen
import com.thoughtworks.visionassistant.app.ui.views.opencv.OpenCVScreen
import com.thoughtworks.visionassistant.app.ui.views.permission.PermissionRequestScreen
import com.thoughtworks.visionassistant.app.utils.navigator.NavigatorImpl

@Composable
fun Navigation(dependency: Dependency) {
    val navController = rememberNavController()
    dependency.setNavigator(NavigatorImpl(navController))

    NavHost(navController = navController, startDestination = Screen.PermissionScreen.route) {
        composable(route = Screen.PermissionScreen.route) {
            PermissionRequestScreen(
                requiredPermissions = arrayOf(
                    android.Manifest.permission.CAMERA,
                ),
                onAllPermissionsGranted = {
                    dependency.navigator.navigateToAbilityConfigScreen()
                }
            )
        }
        composable(route = Screen.AbilityConfigScreen.route) {
            AbilityConfigScreen(dependency)
        }
        composable(route = Screen.OpenCVScreen.route) {
            OpenCVScreen(dependency)
        }
    }
}