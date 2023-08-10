package com.example.mobiusdemo.akashcounter

sealed interface CounterEvent {
    object Increment : CounterEvent
    object Decrement : CounterEvent
}
