package hu.naturlecso.pdpd.features.persons

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import hu.naturlecso.pdpd.domain.model.Organization
import java.math.BigInteger
import java.security.MessageDigest

@BindingAdapter("android:text")
fun TextView.bindOrganizationToText(organization: Organization?) {
    text = organization?.name ?: "-"
}

@BindingAdapter("gravatar")
fun ImageView.bindGravatarToImageView(email: String?) {
    email?.let {
        val hash = it.md5()
        val size = 128

        Picasso.get()
            .load("https://www.gravatar.com/avatar/$hash?s=$size")
            .into(this)
    }
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}
