package com.example.mobiusdemo.memes

import com.example.mobiusdemo.base.ViewRenderer

class MemesUiRenderer(private val ui: MemesUi) : ViewRenderer<MemesModel> {
    override fun render(model: MemesModel) {
        ui.renderMemes(model.memes)
    }
}
