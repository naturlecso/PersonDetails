package hu.naturlecso.pdpd.common.view

interface ItemActionProvider<T> {

    fun setOnItemClickedListener(onItemClickedListener: OnItemClickedListener<T>?)

    interface OnItemClickedListener<T> {
        fun onItemClicked(item: T)
    }
}
