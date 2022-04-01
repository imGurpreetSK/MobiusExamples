package com.example.mobiusdemo.counter

sealed class CounterEvent

object Increment : CounterEvent()
object Decrement : CounterEvent()
