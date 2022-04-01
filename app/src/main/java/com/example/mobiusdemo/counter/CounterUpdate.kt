package com.example.mobiusdemo.counter

import com.example.mobiusdemo.base.next
import com.spotify.mobius.Next
import com.spotify.mobius.Update

class CounterUpdate : Update<CounterModel, CounterEvent, CounterEffect> {
    override fun update(model: CounterModel, event: CounterEvent): Next<CounterModel, CounterEffect> {
        return when(event) {
            Increment -> {
                val updatedModel = model.copy(count = model.count + 1)
                getUpdatedNext(updatedModel)
            }

            Decrement -> {
                val updatedModel = model.copy(count = model.count - 1)
                getUpdatedNext(updatedModel)
            }
        }
    }

    private fun getUpdatedNext(updatedModel: CounterModel): Next<CounterModel, CounterEffect> =
        if (updatedModel.count == 0) {
            next(updatedModel, ZeroCount)
        } else {
            next(updatedModel)
        }
}
