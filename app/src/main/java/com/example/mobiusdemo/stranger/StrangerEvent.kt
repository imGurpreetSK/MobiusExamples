package com.example.mobiusdemo.stranger

sealed class StrangerEvent

class NameInput(val name: String) : StrangerEvent()

class MemesFetched(val count: Int) : StrangerEvent()
