package com.example.mobiusdemo.akashcounter

import com.example.mobiusdemo.base.ViewEffectsHandler

class CounterViewEffectsHandler(
    private val viewActions: CounterViewActions
) : ViewEffectsHandler<CounterEffect.ViewEffect> {

    override fun handle(viewEffect: CounterEffect.ViewEffect) {
        viewActions.notifyUserOfNegativeCount()
    }
}
