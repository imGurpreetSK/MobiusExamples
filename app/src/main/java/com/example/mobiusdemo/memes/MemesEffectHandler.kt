package com.example.mobiusdemo.memes

import com.example.mobiusdemo.stranger.Repository
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx3.RxMobius
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MemesEffectHandler @AssistedInject constructor(
    @Assisted private val viewEffectsConsumer: Consumer<MemesEffect.MemesViewEffect>,
    private val repository: Repository
) {

    fun build(): ObservableTransformer<MemesEffect, MemesEvent> = RxMobius
        .subtypeEffectHandler<MemesEffect, MemesEvent>()
        .addTransformer(MemesEffect.LoadMemes::class.java) {
            it.observeOn(Schedulers.io())
                .switchMap { repository.fetchMemes().toObservable().map { MemesEvent.MemesFetched(it) } }
                .cast(MemesEvent::class.java)
                .onErrorReturn { MemesEvent.FetchFailed }
        }
        .addConsumer(MemesEffect.MemesViewEffect::class.java, viewEffectsConsumer::accept)
        .build()

    @AssistedFactory
    interface Factory {
        fun create(viewEffectsConsumer: Consumer<MemesEffect.MemesViewEffect>): MemesEffectHandler
    }
}
