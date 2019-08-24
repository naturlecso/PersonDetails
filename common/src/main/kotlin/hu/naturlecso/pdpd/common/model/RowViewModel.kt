package hu.naturlecso.pdpd.common.model

import androidx.lifecycle.ViewModel

abstract class RowViewModel<T> protected constructor(val item: T) : ViewModel()
