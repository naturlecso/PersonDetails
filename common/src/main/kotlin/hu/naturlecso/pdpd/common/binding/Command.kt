package hu.naturlecso.pdpd.common.binding

interface Command {
    fun execute()
    fun canExecute(): Boolean
}
