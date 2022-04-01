package com.example.mobiusdemo.counter

import com.example.mobiusdemo.base.ViewRenderer

class CounterViewRenderer(
    private val ui: CounterView
) : ViewRenderer<CounterModel> {

    override fun render(model: CounterModel) {
        ui.updateCount(model.count)
    }
}
