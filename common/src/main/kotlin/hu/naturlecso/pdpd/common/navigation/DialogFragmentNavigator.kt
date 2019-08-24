package hu.naturlecso.pdpd.common.navigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.core.content.res.use
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import hu.naturlecso.pdpd.common.R
import java.util.ArrayDeque
import java.util.Deque

/**
 * https://gist.github.com/matpag/74ce059d590ff571c8240428b274c8c5
 *
 * Allows to navigate to some [DialogFragment].
 *
 * Usage: add some dialog element in your navigation graph
 * ```
 *     <dialog-fragment
 *          android:id="@+id/my_dialog"
 *          android:name="com.example.MyDialogFragment"
 *          tools:layout="@layout/fragment_my_dialog" />
 *
 * ```
 * Use [CustomNavHostFragment] as your [androidx.navigation.NavHost] in your layout
 */
@androidx.navigation.Navigator.Name("dialog-fragment")
class DialogFragmentNavigator(
        private val fragmentManager: FragmentManager
) : androidx.navigation.Navigator<DialogFragmentNavigator.Destination>() {

    private var lastBackStackEntry: FragmentManager.BackStackEntry? = null
    private val backstack: Deque<Int> = ArrayDeque()
    private var pendingPopBackStack = false

    private val onBackstackChangedListener: FragmentManager.OnBackStackChangedListener =
        FragmentManager.OnBackStackChangedListener {
            if (pendingPopBackStack) {
                val entry = fragmentManager.findLastBackStackEntry { it.name == FRAGMENT_BACKSTACK_NAME }
                pendingPopBackStack = (entry != null && entry == lastBackStackEntry)
                lastBackStackEntry = entry
                return@OnBackStackChangedListener
            }
            if (lastBackStackEntry != null && fragmentManager.noneBackStackEntry { it == lastBackStackEntry }) {
                backstack.removeLast()
            }
            lastBackStackEntry = fragmentManager.findLastBackStackEntry { it.name == FRAGMENT_BACKSTACK_NAME }
        }

    override fun navigate(
        destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Extras?
    ): NavDestination? {
        val lastDialogFragment = instantiateFragment(requireNotNull(destination.className))
            .apply { arguments = args }
        val tr = fragmentManager.beginTransaction().addToBackStack(FRAGMENT_BACKSTACK_NAME)

        lastDialogFragment.show(tr, destination.id.toString())
        backstack.addLast(destination.id)

        // we don't want the destination to be added to the NavController stack,
        // because it will update the whole
        // navigation chrome (global AppBar, NavigationView, etc)
        return null
    }

    private fun instantiateFragment(className: String): DialogFragment {
        val clazz = Class.forName(className)
        return fragmentManager.fragmentFactory
            .instantiate(requireNotNull(clazz.classLoader), className) as DialogFragment
    }

    override fun createDestination(): Destination = Destination(this)

    override fun popBackStack(): Boolean {
        val lastDialogFragment =
            fragmentManager.findFragmentByTag(backstack.lastOrNull()?.toString()) as? DialogFragment ?: return false
        lastDialogFragment.dismiss()
        backstack.removeLast()
        pendingPopBackStack = true
        return true
    }

    /* These 2 lifecycle methods should be handled in the NavHost of this navigator.
     * When the NavHost add them it should configure them or call some method so
     * that they can configure there listeners. However, this make a strong coupling between a Navigator and
     * its host implementation.
     *
     * The NavigatorProvider of NavController add a Navigator.OnBackStackChangedListener
     * who calls these methods */
    override fun onBackPressAdded() {
        fragmentManager.addOnBackStackChangedListener(onBackstackChangedListener)
    }

    override fun onBackPressRemoved() {
        fragmentManager.removeOnBackStackChangedListener(onBackstackChangedListener)
    }

    override fun onSaveState(): Bundle? {
        return bundleOf(KEY_BACKSTACK_ID to backstack.toIntArray())
    }

    override fun onRestoreState(savedState: Bundle) {
        savedState.getIntArray(KEY_BACKSTACK_ID)?.let {
            backstack.clear()
            for (id in it) {
                backstack.addLast(id)
            }
        }
        lastBackStackEntry = fragmentManager.findLastBackStackEntry { it.name == FRAGMENT_BACKSTACK_NAME }
    }

    class Destination(navigator: DialogFragmentNavigator) : NavDestination(navigator) {
        var className: String? = null
            get() = checkNotNull(field) { "Dialog name was not set" }

        @Suppress("Recycle")
        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)
            context.resources.obtainAttributes(attrs, R.styleable.DialogFragmentNavigator).use {
                className = it.getString(R.styleable.DialogFragmentNavigator_android_name)
            }
        }
    }

    companion object {
        private const val KEY_BACKSTACK_ID = "com.geekorum.geekdroid:navigation:backstack_ids"
        private const val FRAGMENT_BACKSTACK_NAME = "com.geekorum.geekdroid:navigation:backstack"
    }

    private inline fun FragmentManager.findLastBackStackEntry(
        predicate: (FragmentManager.BackStackEntry) -> Boolean
    ): FragmentManager.BackStackEntry? {
        for (i in backStackEntryCount - 1 downTo 0) {
            val backStackEntry = getBackStackEntryAt(i)
            if (predicate(backStackEntry)) {
                return backStackEntry
            }
        }
        return null
    }

    private inline fun FragmentManager.noneBackStackEntry(
        predicate: (FragmentManager.BackStackEntry) -> Boolean
    ): Boolean {
        for (i in 0 until backStackEntryCount) {
            val backStackEntry = getBackStackEntryAt(i)
            if (predicate(backStackEntry)) {
                return false
            }
        }
        return true
    }
}
