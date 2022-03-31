package com.example.mobiusdemo.stranger

sealed class StrangerEffect

object EmptyName : StrangerEffect()

object FetchMemes : StrangerEffect()

object MemesFetchedAcknowledgement : StrangerEffect()

sealed class StrangerViewEffect : StrangerEffect()
