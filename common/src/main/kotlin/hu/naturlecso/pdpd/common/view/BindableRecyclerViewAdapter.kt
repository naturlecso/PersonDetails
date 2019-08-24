package hu.naturlecso.pdpd.common.view

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.naturlecso.pdpd.common.BR

abstract class BindableRecyclerViewAdapter<T> :
    RecyclerView.Adapter<BindableRecyclerViewAdapter.ViewHolder>(),
    ItemActionProvider<T> {

    protected abstract val diffCallback: DiffUtil.ItemCallback<T>

    private val asyncListDiffer: AsyncListDiffer<T> by lazy { AsyncListDiffer(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(diffCallback).build())
    }

    private var itemClickedListener: ItemActionProvider.OnItemClickedListener<T>? = null

    protected val items: List<T>
        get() = asyncListDiffer.currentList

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            itemClickedListener?.onItemClicked(item)
        }

        val binding = holder.binding
        binding.setVariable(BR.vm, getItemViewModel(item))
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    fun getItemPosition(item: T): Int {
        return asyncListDiffer.currentList.indexOf(item)
    }

    protected fun getItem(position: Int): T {
        return asyncListDiffer.currentList[position]
    }

    @CallSuper
    fun swapItems(newItems: List<T>) {
        asyncListDiffer.submitList(newItems)
    }

    override fun setOnItemClickedListener(onItemClickedListener: ItemActionProvider.OnItemClickedListener<T>?) {
        this.itemClickedListener = onItemClickedListener
    }

    protected abstract fun getItemViewModel(item: T): ViewModel

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            this.binding.executePendingBindings()
        }
    }
}
