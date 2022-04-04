package com.example.mobiusdemo.memes

import com.example.mobiusdemo.base.ViewEffectsHandler
import com.example.mobiusdemo.memes.MemesEffect.*

class MemesUiEffectsHandler(
    private val uiActions: MemesUiActions
) : ViewEffectsHandler<MemesViewEffect> {

    override fun handle(viewEffect: MemesViewEffect) {
        when (viewEffect) {
            MemesFetchSuccessful -> uiActions.showFetchSuccessful()
            ShowRetry -> uiActions.showRetry()
            Loading -> uiActions.showLoading()
        }
    }
}
