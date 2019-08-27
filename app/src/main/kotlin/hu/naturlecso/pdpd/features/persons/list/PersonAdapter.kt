package hu.naturlecso.pdpd.features.persons.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.view.BindableRecyclerViewAdapter
import hu.naturlecso.pdpd.databinding.ListItemPersonBinding
import hu.naturlecso.pdpd.domain.model.Person

class PersonAdapter : BindableRecyclerViewAdapter<Person>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemPersonBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_person,
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun getItemViewModel(item: Person): ViewModel = PersonListItemViewModel(item)

    override val diffCallback: DiffUtil.ItemCallback<Person> = object : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem == newItem
    }
}
