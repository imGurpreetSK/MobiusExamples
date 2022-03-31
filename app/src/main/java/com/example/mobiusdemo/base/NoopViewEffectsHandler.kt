package com.example.mobiusdemo.base

class NoopViewEffectsHandler<V> : ViewEffectsHandler<V> {

    override fun handle(viewEffect: V) {
        // Noop
    }
}
