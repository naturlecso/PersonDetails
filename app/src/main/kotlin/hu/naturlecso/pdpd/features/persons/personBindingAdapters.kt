package hu.naturlecso.pdpd.features.persons

import android.widget.TextView
import androidx.databinding.BindingAdapter
import hu.naturlecso.pdpd.domain.model.Organization

@BindingAdapter("android:text")
fun TextView.bindOrganizationToText(organization: Organization?) {
    text = organization?.name ?: "-"
}
