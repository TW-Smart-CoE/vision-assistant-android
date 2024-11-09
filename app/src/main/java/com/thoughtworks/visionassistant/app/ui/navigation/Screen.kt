package com.thoughtworks.visionassistant.app.ui.navigation

sealed class Screen(val route: String) {
    data object PermissionScreen : Screen("permission_screen")
    data object AbilityConfigScreen : Screen("ability_config_screen")
    data object OpenCVScreen : Screen("opencv_screen")

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

