package hu.naturlecso.pdpd.features.persons.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.view.BindableRecyclerViewAdapter
import hu.naturlecso.pdpd.databinding.ListItemContactBinding
import hu.naturlecso.pdpd.domain.model.ContactDetails

class ContactAdapter : BindableRecyclerViewAdapter<ContactDetails>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemContactBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_contact,
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun getItemViewModel(item: ContactDetails): ViewModel = ContactListItemViewModel(item)

    override val diffCallback: DiffUtil.ItemCallback<ContactDetails> = object : DiffUtil.ItemCallback<ContactDetails>() {
        override fun areItemsTheSame(oldItem: ContactDetails, newItem: ContactDetails): Boolean = oldItem.value == newItem.value
        override fun areContentsTheSame(oldItem: ContactDetails, newItem: ContactDetails): Boolean = oldItem == newItem
    }
}
