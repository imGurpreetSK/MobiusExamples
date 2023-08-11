package com.example.mobiusdemo.akashcounter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.mobiusdemo.base.BaseScreen
import com.example.mobiusdemo.base.ViewEffectsHandler
import com.example.mobiusdemo.base.ViewRenderer
import com.example.mobiusdemo.counter.CounterScreenKey
import com.example.mobiusdemo.databinding.CounterFragmentBinding
import com.jakewharton.rxbinding4.view.clicks
import com.spotify.mobius.Update
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx3.RxMobius
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer

class AkashCounterFragment : BaseScreen<
        CounterScreenKey,
        CounterFragmentBinding,
        CounterModel,
        CounterEvent,
        CounterEffect,
        CounterEffect.ViewEffect
>(), CounterUiActions, CounterViewActions {

    override fun defaultModel(): CounterModel = CounterModel(0)

    override fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): CounterFragmentBinding =
        CounterFragmentBinding.inflate(layoutInflater, container, false)

    override fun createUpdate(): Update<CounterModel, CounterEvent, CounterEffect> = CounterUpdate

    override fun events(): Observable<CounterEvent> = Observable.merge(
        binding.incrementButton.clicks().map { CounterEvent.Increment },
        binding.decrementButton.clicks().map { CounterEvent.Decrement }
    )

    override fun uiRenderer(): ViewRenderer<CounterModel> = CounterUiRenderer(this)

    override fun viewEffectHandler(): ViewEffectsHandler<CounterEffect.ViewEffect> = CounterViewEffectsHandler(this)

    override fun createEffectHandler(viewEffectsConsumer: Consumer<CounterEffect.ViewEffect>): ObservableTransformer<CounterEffect, CounterEvent> =
        RxMobius.subtypeEffectHandler<CounterEffect, CounterEvent>()
            .addConsumer(CounterEffect.ViewEffect::class.java, viewEffectsConsumer::accept, AndroidSchedulers.mainThread())
            .build()

    override fun showCount(count: Int) {
        binding.countTextView.text = count.toString()
    }

    override fun notifyUserOfNegativeCount() {
        Toast.makeText(requireContext(), "Negative count not allowed!", Toast.LENGTH_SHORT).show()
    }
}
