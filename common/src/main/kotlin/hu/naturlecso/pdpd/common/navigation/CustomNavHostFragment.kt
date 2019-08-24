package hu.naturlecso.pdpd.common.navigation

import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign

/**
 * A [NavHostFragment] who supports navigation to [DialogFragment]s.
 */
class CustomNavHostFragment : NavHostFragment() {

    override fun createFragmentNavigator(): androidx.navigation.Navigator<out FragmentNavigator.Destination> {
        navController.navigatorProvider += DialogFragmentNavigator(childFragmentManager)
        return super.createFragmentNavigator()
    }
}
