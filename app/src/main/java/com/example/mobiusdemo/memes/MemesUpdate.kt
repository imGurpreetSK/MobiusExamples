package com.example.mobiusdemo.memes

import com.example.mobiusdemo.base.dispatch
import com.example.mobiusdemo.base.next
import com.spotify.mobius.Next
import com.spotify.mobius.Update

class MemesUpdate : Update<MemesModel, MemesEvent, MemesEffect> {
    override fun update(model: MemesModel, event: MemesEvent): Next<MemesModel, MemesEffect> {
        return when(event) {
            is MemesEvent.MemesFetched -> next(
                model.copy(memes = model.memes + event.memes),
                MemesEffect.MemesFetchSuccessful
            )

            MemesEvent.Retry -> dispatch(MemesEffect.Loading, MemesEffect.LoadMemes)

            MemesEvent.FetchFailed -> dispatch(MemesEffect.ShowRetry)
        }
    }
}
