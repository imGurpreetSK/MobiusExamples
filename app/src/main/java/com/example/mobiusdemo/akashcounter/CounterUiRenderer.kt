package com.example.mobiusdemo.akashcounter

import com.example.mobiusdemo.base.ViewRenderer

class CounterUiRenderer(private val uiActions: CounterUiActions) : ViewRenderer<CounterModel> {

    override fun render(model: CounterModel) {
        uiActions.showCount(model.count)
    }
}
