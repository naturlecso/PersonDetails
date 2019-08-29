package hu.naturlecso.pdpd.common.util

import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.disposables.Disposable
import org.reactivestreams.Publisher

fun <T> Publisher<T>.asLiveData() = LiveDataReactiveStreams.fromPublisher(this)

fun Disposable?.disposeIfNeeded() {
    if (this != null && !isDisposed) {
        dispose()
    }
}
