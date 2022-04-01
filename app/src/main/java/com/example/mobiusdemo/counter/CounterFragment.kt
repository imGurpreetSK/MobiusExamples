package com.example.mobiusdemo.counter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobiusdemo.base.BaseScreen
import com.example.mobiusdemo.base.ScreenKey
import com.example.mobiusdemo.base.ViewRenderer
import com.example.mobiusdemo.databinding.CounterFragmentBinding
import com.jakewharton.rxbinding4.view.clicks
import com.spotify.mobius.Update
import com.spotify.mobius.functions.Consumer
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class CounterFragment : BaseScreen<
        CounterScreenKey,
        CounterFragmentBinding,
        CounterModel,
        CounterEvent,
        CounterEffect,
        Unit
        >(), CounterView {

    @Inject
    lateinit var counterEffectHandler: CounterEffectHandler.Factory

    override fun defaultModel(): CounterModel = CounterModel(0)

    override fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): CounterFragmentBinding =
        CounterFragmentBinding.inflate(layoutInflater, container, false)

    override fun createUpdate(): Update<CounterModel, CounterEvent, CounterEffect> = CounterUpdate()

    override fun uiRenderer(): ViewRenderer<CounterModel> = CounterViewRenderer(this)

    override fun events(): Observable<CounterEvent> = Observable.merge(
        binding.incrementButton.clicks().map { Increment },
        binding.decrementButton.clicks().map { Decrement }
    )

    override fun createEffectHandler(
        viewEffectsConsumer: Consumer<Unit>
    ): ObservableTransformer<CounterEffect, CounterEvent> = counterEffectHandler
        .create(viewEffectsConsumer)
        .build(requireContext())

    override fun updateCount(count: Int) {
        binding.countTextView.text = count.toString()
    }
}

@Parcelize
class CounterScreenKey : ScreenKey() {
    override val analyticsName: String
        get() = "Counter screen"

    override fun instantiateFragment(): Fragment {
        TODO("Not yet implemented")
    }
}
