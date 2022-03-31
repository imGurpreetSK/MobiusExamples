package com.example.mobiusdemo.base

// Forces when blocks to be exhaustive.
fun Unit.exhaustive() {}

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
