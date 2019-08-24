package hu.naturlecso.pdpd.common.binding

fun command(canExecute: () -> Boolean = { true }, action: () -> Unit): Command
        = RelayCommand(action, canExecute)

fun <T> command(canExecute: () -> Boolean = { true }, action: (T) -> Unit): (T) -> Command
        = { param -> RelayCommand({ action.invoke(param) }, canExecute) }
