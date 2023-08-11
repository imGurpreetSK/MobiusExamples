package com.example.mobiusdemo.akashcounter

import com.example.mobiusdemo.base.dispatch
import com.example.mobiusdemo.base.next
import com.spotify.mobius.Next
import com.spotify.mobius.Update

object CounterUpdate : Update<CounterModel, CounterEvent, CounterEffect> {

    override fun update(model: CounterModel, event: CounterEvent): Next<CounterModel, CounterEffect> {
        return when (event) {
            CounterEvent.Decrement -> if (model.count > 0) {
                next(model.copy(count = model.count - 1))
            } else {
                dispatch(CounterEffect.ViewEffect.NegativeCountNotAllowed)
            }
            CounterEvent.Increment -> next(model.copy(count = model.count + 1))
        }
    }
}
