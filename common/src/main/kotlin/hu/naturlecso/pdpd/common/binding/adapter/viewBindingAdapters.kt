package hu.naturlecso.pdpd.common.binding.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:visibility", "invisibleType"], requireAll = false)
fun View.bindVisibility(visible: Boolean, invisibleType: Int?) {
    visibility = decideVisibility(visible, invisibleType)
}

private fun decideVisibility(visible: Boolean, invisibleType: Int?): Int {
    return if (visible) {
        View.VISIBLE
    } else {
        invisibleType ?: View.GONE
    }
}
