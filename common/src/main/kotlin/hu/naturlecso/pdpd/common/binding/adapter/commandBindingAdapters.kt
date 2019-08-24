package hu.naturlecso.pdpd.common.binding.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import hu.naturlecso.pdpd.common.binding.Command

@BindingAdapter("command")
fun View.bindCommand(command: Command?) {
    bindCommandToView(this, command)
}

@BindingAdapter(value = ["command", "item"])
fun <T> View.bindCommandWithParam(commandWithParam: ((T) -> Command)?, param: T) {
    bindCommandToView(this, commandWithParam?.invoke(param))
}

private fun bindCommandToView(view: View, command: Command?) {
    if (command != null) {
        view.isEnabled = command.canExecute()
        view.setOnClickListener { command.execute() }
    } else {
        view.isEnabled = false
        view.setOnClickListener(null)
    }
}
