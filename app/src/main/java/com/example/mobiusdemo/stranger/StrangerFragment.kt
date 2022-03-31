package com.example.mobiusdemo.stranger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobiusdemo.base.BaseScreen
import com.example.mobiusdemo.base.ScreenKey
import com.example.mobiusdemo.base.ViewRenderer
import com.example.mobiusdemo.databinding.StrangerFragmentBinding
import com.jakewharton.rxbinding4.widget.textChanges
import com.spotify.mobius.Update
import com.spotify.mobius.functions.Consumer
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class StrangerFragment : BaseScreen<
        StrangerScreenKey,
        StrangerFragmentBinding,
        StrangerModel,
        StrangerEvent,
        StrangerEffect,
        StrangerViewEffect
        >(), StrangerView {

    @Inject
    lateinit var strangerEffectHandler: StrangerEffectHandler.Factory

    override fun defaultModel(): StrangerModel = StrangerModel("", 0)

    override fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): StrangerFragmentBinding =
        StrangerFragmentBinding.inflate(layoutInflater, container, false)

    override fun events(): Observable<StrangerEvent> = binding.input.textChanges().map { NameInput(it.toString()) }

    override fun createUpdate(): Update<StrangerModel, StrangerEvent, StrangerEffect> = StrangerUpdate()

    override fun uiRenderer(): ViewRenderer<StrangerModel> = StrangerUiRenderer(this)

    override fun createEffectHandler(
        viewEffectsConsumer: Consumer<StrangerViewEffect>
    ): ObservableTransformer<StrangerEffect, StrangerEvent> = strangerEffectHandler
        .create(viewEffectsConsumer)
        .build(requireContext())

    override fun renderGreeting(name: String) {
        binding.output.text = name
    }
}

@Parcelize
class StrangerScreenKey(override val analyticsName: String = "Stranger greeting") : ScreenKey() {
    override fun instantiateFragment(): Fragment {
        return StrangerFragment()
    }
}
