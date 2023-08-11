package com.example.mobiusdemo.akashcounter

import com.google.common.truth.Truth.assertThat
import com.spotify.mobius.Next
import org.junit.Assert.assertEquals
import org.junit.Test

class CounterUpdateTest {

    @Test
    fun `update count when increment event is received`() {
        // Setup
        val model = CounterModel(0)
        val event = CounterEvent.Increment

        // Act
        val result = CounterUpdate.update(model, event)

        // Assert
        val expected = Next.next<CounterModel, Unit>(CounterModel(1))
        assertEquals(expected, result)
    }

    @Test
    fun `update count when increment event is received - second test`() {
        // Setup
        val model = CounterModel(3)
        val event = CounterEvent.Increment

        // Act
        val result = CounterUpdate.update(model, event)

        // Assert
        val expected = Next.next<CounterModel, Unit>(CounterModel(4))
        assertEquals(expected, result)
    }

    @Test
    fun `reduce count when decrement event is received`() {
        // Setup
        val model = CounterModel(5)
        val event = CounterEvent.Decrement

        // Act
        val result = CounterUpdate.update(model, event)

        // Assert
        val expected = Next.next<CounterModel, Unit>(CounterModel(4))
        assertEquals(expected, result)
    }

    @Test
    fun `reduce count when decrement event is received - second test`() {
        // Setup
        val model = CounterModel(50)
        val event = CounterEvent.Decrement

        // Act
        val result = CounterUpdate.update(model, event)

        // Assert
        val expected = Next.next<CounterModel, Unit>(CounterModel(49))
        assertEquals(expected, result)
    }

    @Test
    fun `do not allow negative count - base count 0`() {
        // Setup
        val model = CounterModel(0)
        val event = CounterEvent.Decrement

        // Act
        val result = CounterUpdate.update(model, event)

        // Assert
        assertThat(result.hasModel())
            .isFalse()
        assertThat(result.effects())
            .containsExactly(CounterEffect.ViewEffect.NegativeCountNotAllowed)
    }
}
