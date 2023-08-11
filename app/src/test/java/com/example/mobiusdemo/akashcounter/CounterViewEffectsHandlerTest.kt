package com.example.mobiusdemo.akashcounter

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CounterViewEffectsHandlerTest {

    @Test
    fun `notify user of negative count disallowed`() {
        // Setup
        val fakeViewActions = object : CounterViewActions {
            var count = 0

            override fun notifyUserOfNegativeCount() {
                count += 1
            }

            fun resetState() {
                count = 0
            }
        }
        val counterViewEffectsHandler = CounterViewEffectsHandler(fakeViewActions)

        // Act
        counterViewEffectsHandler.handle(CounterEffect.ViewEffect.NegativeCountNotAllowed)

        // Assert
        assertThat(fakeViewActions.count)
            .isEqualTo(1)

        // Teardown
        fakeViewActions.resetState()
    }
}
