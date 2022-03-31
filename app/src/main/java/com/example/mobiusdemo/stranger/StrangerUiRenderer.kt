package com.example.mobiusdemo.stranger

import com.example.mobiusdemo.base.ViewRenderer

class StrangerUiRenderer(private val view: StrangerView) : ViewRenderer<StrangerModel> {
    override fun render(model: StrangerModel) {
        view.renderGreeting(model.name)
    }
}
