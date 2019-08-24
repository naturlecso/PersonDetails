package hu.naturlecso.pdpd.common.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    data class BackTo(val destinationId: Int): NavigationCommand()
    object Back: NavigationCommand()
    object ToRoot: NavigationCommand()
}
