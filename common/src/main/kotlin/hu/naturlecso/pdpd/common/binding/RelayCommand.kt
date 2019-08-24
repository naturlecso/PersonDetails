package hu.naturlecso.pdpd.common.binding

import androidx.databinding.BaseObservable
import timber.log.Timber

class RelayCommand(
    private val executable: () -> Unit,
    private val canExecute: () -> Boolean = { true }
): BaseObservable(), Command {

    override fun execute() = executable.invoke()

    override fun canExecute(): Boolean = try {
        canExecute.invoke()
    } catch (e: Exception) {
        Timber.v(e)
        false
    }
}
