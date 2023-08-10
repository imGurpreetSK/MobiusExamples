package com.example.mobiusdemo.akashcounter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mobiusdemo.base.BaseScreen
import com.example.mobiusdemo.base.ViewRenderer
import com.example.mobiusdemo.counter.CounterScreenKey
import com.example.mobiusdemo.databinding.CounterFragmentBinding
import com.jakewharton.rxbinding4.view.clicks
import com.spotify.mobius.Update
import io.reactivex.rxjava3.core.Observable

class AkashCounterFragment : BaseScreen<
        CounterScreenKey,
        CounterFragmentBinding,
        CounterModel,
        CounterEvent,
        Unit,
        Unit
>(), CounterUiActions {

    override fun defaultModel(): CounterModel = CounterModel(0)

    override fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): CounterFragmentBinding =
        CounterFragmentBinding.inflate(layoutInflater, container, false)

    override fun createUpdate(): Update<CounterModel, CounterEvent, Unit> = CounterUpdate

    override fun events(): Observable<CounterEvent> = Observable.merge(
        binding.incrementButton.clicks().map { CounterEvent.Increment },
        binding.decrementButton.clicks().map { CounterEvent.Decrement }
    )

    override fun uiRenderer(): ViewRenderer<CounterModel> = CounterUiRenderer(this)

    override fun showCount(count: Int) {
        binding.countTextView.text = count.toString()
    }
}
