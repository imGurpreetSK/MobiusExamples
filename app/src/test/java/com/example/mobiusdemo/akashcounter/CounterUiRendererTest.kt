package com.example.mobiusdemo.akashcounter

import org.junit.Assert.assertEquals
import org.junit.Test

class CounterUiRendererTest {

    private val fakeUiActions = object : CounterUiActions {
        var shownCount: Int? = null

        override fun showCount(count: Int) {
            shownCount = count
        }

        fun clearCount() {
            shownCount = null
        }
    }

    @Test
    fun `show latest count to user`() {
        // Setup
        val uiRenderer = CounterUiRenderer(fakeUiActions)

        // Act
        uiRenderer.render(CounterModel(1))

        // Assert
        assertEquals(1, fakeUiActions.shownCount)

        // Tear down
        fakeUiActions.clearCount()
    }

    @Test
    fun `show latest count to user - second test`() {
        // Setup
        val uiRenderer = CounterUiRenderer(fakeUiActions)

        // Act
        uiRenderer.render(CounterModel(5))

        // Assert
        assertEquals(5, fakeUiActions.shownCount)

        // Tear down
        fakeUiActions.clearCount()
    }
}
