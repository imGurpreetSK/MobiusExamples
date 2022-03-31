package com.example.mobiusdemo.stranger

import com.example.mobiusdemo.base.next
import com.spotify.mobius.Next
import com.spotify.mobius.Update

class StrangerUpdate : Update<StrangerModel, StrangerEvent, StrangerEffect> {

    override fun update(model: StrangerModel, event: StrangerEvent): Next<StrangerModel, StrangerEffect> {
        return when(event) {
            is NameInput -> {
                if (model.name.isNotBlank()) {
                    next(model.copy(name = event.name))
                } else {
                    next(model.copy(name = event.name), EmptyName, FetchMemes)
                }
            }

            is MemesFetched -> next(model.copy(memesCount = event.count), MemesFetchedAcknowledgement)
        }
    }
}
