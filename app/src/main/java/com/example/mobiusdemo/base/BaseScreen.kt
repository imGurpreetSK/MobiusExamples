package com.example.mobiusdemo.base

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import com.spotify.mobius.*
import com.spotify.mobius.Next.noChange
import com.spotify.mobius.android.MobiusLoopViewModel
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx3.RxMobius
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseScreen<K : ScreenKey, B : ViewBinding, M : Parcelable, E, F, V> : Fragment() {

    companion object {
        private const val KEY_MODEL = "com.example.mobiusdemo.base.BaseScreen.KEY_MODEL"
    }

    private lateinit var viewModel: MobiusLoopViewModel<M, E, F, V>

    protected val screenKey by unsafeLazy { ScreenKey.key<K>(this) }

    val screenName: String
        get() = screenKey.analyticsName

    private var _binding: B? = null

    protected val binding: B
        get() = _binding!!

    private lateinit var eventsDisposable: Disposable

    abstract fun defaultModel(): M

    abstract fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): B

    open fun uiRenderer(): ViewRenderer<M> = NoopViewRenderer()

    open fun viewEffectHandler(): ViewEffectsHandler<V> = NoopViewEffectsHandler()

    open fun events(): Observable<E> = Observable.never()

    open fun createUpdate(): Update<M, E, F> = Update { _, _ -> noChange() }

    open fun createInit(): Init<M, F> = Init { model -> first(model) }

    open fun createEffectHandler(viewEffectsConsumer: Consumer<V>): ObservableTransformer<F, E> =
        ObservableTransformer { Observable.never() }

    open fun additionalEventSources(): List<EventSource<E>> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView(inflater, container)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startModel = savedInstanceState?.getParcelable(KEY_MODEL) ?: defaultModel()

        viewModel = ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {
            private fun loop(viewEffectsConsumer: Consumer<V>) = RxMobius
                .loop(createUpdate(), createEffectHandler(viewEffectsConsumer))
                .eventSources(additionalEventSources())
                .logger(logger())

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MobiusLoopViewModel.create<M, E, F, V>(
                    ::loop,
                    startModel,
                    createInit()
                ) as T
            }
        }).get()

        val uiRenderer = uiRenderer()
        viewModel.models.observe(viewLifecycleOwner, uiRenderer::render)

        val viewEffectHandler = viewEffectHandler()
        viewModel.viewEffects.setObserver(
            viewLifecycleOwner,
            { liveViewEffect -> viewEffectHandler.handle(liveViewEffect) },
            { pausedViewEffects -> pausedViewEffects.forEach(viewEffectHandler::handle) }
        )

        eventsDisposable = events().subscribe {
            viewModel.dispatchEvent(it!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventsDisposable.dispose()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::viewModel.isInitialized) {
            outState.putParcelable(KEY_MODEL, viewModel.model)
        }
    }

    private fun logger() = object : MobiusLoop.Logger<M, E, F> {
        override fun beforeInit(model: M) {
            // No-Op
        }

        override fun afterInit(model: M, result: First<M, F>) {
            // No-Op
        }

        override fun exceptionDuringInit(model: M, exception: Throwable) {
            // No-Op
        }

        override fun beforeUpdate(model: M, event: E) {
            // No-Op
        }

        override fun afterUpdate(model: M, event: E, result: Next<M, F>) {
            Log.i("AfterUpdate", "$model, $event, $result")
        }

        override fun exceptionDuringUpdate(model: M, event: E, exception: Throwable) {
            // No-Op
        }
    }
}
