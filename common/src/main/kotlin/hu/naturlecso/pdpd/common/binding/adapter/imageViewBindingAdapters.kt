package hu.naturlecso.pdpd.common.binding.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["url", "placeholder", "size"])
fun ImageView.bindUrl(url: String?, placeholder: Drawable?, size: Float) {
    url ?: return
    placeholder ?: return

    Picasso.get()
            .load(url)
            .resize(size.toInt(), size.toInt())
            .placeholder(placeholder)
            .into(this)
}
