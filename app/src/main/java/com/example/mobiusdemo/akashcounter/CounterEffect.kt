package com.example.mobiusdemo.akashcounter

sealed interface CounterEffect {
    sealed interface ViewEffect : CounterEffect {
        object NegativeCountNotAllowed : ViewEffect
    }
}
