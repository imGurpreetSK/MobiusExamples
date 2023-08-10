package com.example.mobiusdemo.akashcounter

import com.example.mobiusdemo.base.next
import com.spotify.mobius.Next
import com.spotify.mobius.Update

object CounterUpdate : Update<CounterModel, CounterEvent, Unit> {

    override fun update(model: CounterModel, event: CounterEvent): Next<CounterModel, Unit> {
        return when (event) {
            CounterEvent.Decrement -> next(model.copy(count = model.count - 1))
            CounterEvent.Increment -> next(model.copy(count = model.count + 1))
        }
    }
}
