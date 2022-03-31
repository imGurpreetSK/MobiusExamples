package com.example.mobiusdemo.stranger

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx3.RxMobius
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

class StrangerEffectHandler @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val viewEffectsConsumer: Consumer<StrangerViewEffect>
) {

    @AssistedFactory
    interface Factory {
        fun create(viewEffectsConsumer: Consumer<StrangerViewEffect>): StrangerEffectHandler
    }

    fun build(context: Context): ObservableTransformer<StrangerEffect, StrangerEvent> {
        return RxMobius.subtypeEffectHandler<StrangerEffect, StrangerEvent>()
            .addAction(EmptyName::class.java) { Log.i("", "BEEEEEEEP!") }
            .addAction(MemesFetchedAcknowledgement::class.java, {
                Toast.makeText(context, "Fetched!", Toast.LENGTH_SHORT).show()
            }, AndroidSchedulers.mainThread())
            .addTransformer(FetchMemes::class.java) {
                it.observeOn(Schedulers.io())
                    .switchMap { repository.fetchMemes().toObservable() }
                    .doOnNext { Log.i("", it.count().toString()) }
                    .map { MemesFetched(it.count()) }
            }
            .addConsumer(StrangerViewEffect::class.java, viewEffectsConsumer::accept)
            .build()
    }
}
